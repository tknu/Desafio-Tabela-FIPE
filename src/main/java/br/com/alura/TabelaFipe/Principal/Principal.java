package br.com.alura.TabelaFipe.Principal;

import br.com.alura.TabelaFipe.Model.Dados;
import br.com.alura.TabelaFipe.Model.Modelos;
import br.com.alura.TabelaFipe.Model.Veiculo;
import br.com.alura.TabelaFipe.Service.ConsumoApi;
import br.com.alura.TabelaFipe.Service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";

    public void exibeMenu() {
        var menu = """
                *** Opções ***
                Carro
                Moto
                Caminhão
                **************
                Digite uma das opções para pesquisar.
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco = "";

        if (opcao.equalsIgnoreCase("carro")) {
            endereco = URL_BASE + "/carros/marcas/";
        } else if (opcao.equalsIgnoreCase("moto")) {
            endereco = URL_BASE + "/motos/marcas/";
        } else if (opcao.equalsIgnoreCase("caminhão")) {
            endereco = URL_BASE + "/caminhoes/marcas/";
        } else {
            System.out.println("Opção inválida");
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + codigoMarca + "/modelos/";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite o nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nPor favor, digite o código do modelo");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + codigoModelo + "/anos/";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
