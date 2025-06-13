package br.com.alura.TabelaFipe.Principal;

import br.com.alura.TabelaFipe.Service.ConsumoApi;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

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
    }
}
