package visao.layout;
import javax.swing.*;
import java.awt.*;

public class Botao extends JButton {

    public Botao(Icon icone){
        super(icone);
        this.configuracaoPadrao();
    }
    public Botao(String nome){
        super(nome);
        this.configuracaoPadrao();
    }

    public void configurarFonteCorFundo(Font fonte, Color corFonte, Color corFundo){
        this.setFont(fonte);
        this.setForeground(corFonte);
        this.setBackground(corFundo);
    }

    private void configuracaoPadrao() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

}
