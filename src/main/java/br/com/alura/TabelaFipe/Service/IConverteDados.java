package br.com.alura.TabelaFipe.Service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> obeterLista(String json, Class<T> classe);
}
