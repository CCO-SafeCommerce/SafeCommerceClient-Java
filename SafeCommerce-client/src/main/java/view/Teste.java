package view;

import com.github.britooo.looca.api.core.Looca;

public class Teste {
    public static void main(String[] args) {
        Looca looca = new Looca();
        Long lDisco = looca.getGrupoDeDiscos().getDiscos().stream().mapToLong(d -> d.getBytesDeLeitura()).sum();
        Double eDisco = looca.getGrupoDeDiscos().getDiscos().stream().mapToDouble(d -> d.getBytesDeEscritas()).sum();
        System.out.println(lDisco);
        System.out.println(eDisco);
    }
}
