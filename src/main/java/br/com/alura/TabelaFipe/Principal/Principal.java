package br.com.alura.TabelaFipe.Principal;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

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
        String endereco;
    }
}
