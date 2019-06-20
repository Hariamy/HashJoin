package visao.layout;

import java.awt.*;
import java.io.File;

public class Fontes {

    public static final Font ROBOTO_PEQUENA = new Font("Roboto", Font.PLAIN, 25);
    public static final Font ROBOTO_MENOR = new Font("Roboto", Font.PLAIN, 21);
    public static final Font ROBOTO_MEDIA = new Font("Roboto", Font.PLAIN, 30);
    public static final Font ROBOTO_GRANDE = new Font("Roboto", Font.PLAIN, 35);
    public static final Font ROBOTO_BOLD_PEQUENA = new Font("Roboto", Font.BOLD, 25);
    public static final Font ROBOTO_BOLD_MENOR = new Font("Roboto", Font.BOLD, 21);;
    public static final Font ROBOTO_BOLD_MEDIA = new Font("Roboto", Font.BOLD, 30);
    public static final Font ROBOTO_BOLD_GRANDE = new Font("Roboto", Font.BOLD, 35);
    public static final Font TITULO = new Font("Roboto", Font.BOLD, 20);

    public static void setFonte(){
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonte/Roboto-Black.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonte/Roboto-Bold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonte/Roboto-Light.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonte/Roboto-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonte/Roboto-Medium.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonte/Roboto-Thin.ttf")));

        } catch (Exception e) {
            System.out.println("Erro ao carregar fonte: " + e);
        }
    }
}
