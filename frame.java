import javax.swing.*;
public class frame extends JFrame {
    frame(){//constructer
        //adding the panel to the game
        this.add(new panel());
        //naming the frame window
        this.setTitle("SnakeGame");
        this.setResizable(false);//cannot allow to resize window
        this.setVisible(true);
        this.pack();//resize resolution according to device
    }
}
