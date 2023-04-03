/*
package br.edu.infnet.TechStore.model.tests;

import br.edu.infnet.TechStore.model.domain.Teclado;
import br.edu.infnet.TechStore.model.execptions.DescontoException;
import br.edu.infnet.TechStore.model.execptions.ProdutoException;

public class TecladoTeste {
    public static void main(String[] args) {

        try {
            Teclado p1 = new Teclado(3,250F,1);
            p1.setMarca("Redragon");
            p1.setModelo("Mitra");
            p1.setBluetooh_cable("Cable");
            p1.setIluminacao(true);

            p1.setGhosting("Full");
            p1.setTipo("Mecanico");
            p1.setSwitch_type("Brown");
            p1.calcularDesconto();
            p1.imprimir_pedido();

        }catch (ProdutoException | DescontoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }

        try {
            Teclado p2 = new Teclado(3,250F,1);
            p2.setMarca("Redragon");
            p2.setModelo("Mitra");
            p2.setBluetooh_cable("Cable");
            p2.setIluminacao(true);

            p2.setTipo("membrana");
            p2.setGhosting("nenhum");
            p2.setSwitch_type("nenhum");
            p2.calcularDesconto();
            p2.imprimir_pedido();

        }catch (ProdutoException | DescontoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }

        //Caso de erro com valor errado
        try {
            Teclado p3 = new Teclado(5,0F,1);
            p3.setMarca("Redragon");
            p3.setModelo("Mitra");
            p3.setBluetooh_cable("Cable");
            p3.setIluminacao(true);

            p3.setGhosting("Full");
            p3.setTipo("Mecanico");
            p3.setSwitch_type("Brown");
            p3.calcularDesconto();
            p3.imprimir_pedido();

        }catch (ProdutoException | DescontoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }
}
*/
