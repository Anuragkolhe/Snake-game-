import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
//import java.awt.event.ActionListener;

public class panel extends JPanel implements ActionListener {
    static int width = 1200;
    static int height = 600;
    static int unit = 50;
    Timer timer;//to specify time in which new frame is displayed
    //deciding food location randomely
    Random random;
    int foodx,foody;

    int score;
    int length = 3;

    char  dir = 'R';//direction of snake in starting always left to right

    //to specify the game is inded or not
    Boolean flag = false;
    static int delay = 160;

    int xsnake[] = new int[288];
    int ysnake[] = new int[288];

    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);

        //this command allows/UNABLE keyboard input to be process
        this.setFocusable(true);
        random = new Random();
        //creating abstract class for input processing
        this.addKeyListener(new mykey());

        gamestart();
    }

    public  void gamestart(){
        spawnfood();
        flag = true;
        timer = new Timer(delay,this);
        timer.start();//160milisecond
    }
    public void spawnfood(){
        foodx = random.nextInt(width/unit)*unit;
        foodx = random.nextInt(width/unit)*unit;
    }
    public void paintComponent(Graphics graphic){//it is class having on j panel its help to drawing evrything in the panel
        super.paintComponent(graphic);//this called paint component from parent class(jpanel)
        draw(graphic);

    }
    public void draw(Graphics graphic){
        if(flag == true){
            graphic.setColor(Color.RED);
            graphic.fillOval(foodx,foody,unit,unit);

            for(int i=0;i<length;i++){
                if(i==0){
                    graphic.setColor(Color.orange);
                }
                else{
                    graphic.setColor(Color.GREEN);
                }
                graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
            }
            graphic.setColor(Color.cyan);
            graphic.setFont(new Font("comic sans",Font.BOLD,40));
            FontMetrics f = getFontMetrics(graphic.getFont());
            graphic.drawString("Score: "+score, (width-f.stringWidth("Score:"+score))/2,graphic.getFont().getSize());
        }
        else{
            gameover(graphic);
        }
    }

    public void gameover(Graphics graphic){

        //to display the score
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        FontMetrics f = getFontMetrics(graphic.getFont());
        graphic.drawString("Score: "+score, (width-f.stringWidth("Score:"+score))/2,graphic.getFont().getSize());

           //to display the gameover text
        graphic.setColor(Color.red);
        graphic.setFont(new Font("comic sans",Font.BOLD,80));
        FontMetrics f2 = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!"+score, (width-f2.stringWidth("GAME OVER!"+score))/2,height/2);


        //to display the replay prompt
        graphic.setColor(Color.green);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        graphic.drawString("Press R to replay"+score, (width-f.stringWidth("Press R to replay"+score))/2,height/2+150);
    }
    public  void checkhit(){
        if(xsnake[0]<0){
            flag = false;
        }
        else if(xsnake[0] >1200){
            flag = false;
        }
        else if(ysnake[0] <0){
            flag = false;
        }
        else if(ysnake[0] >600){
            flag = false;
        }

        for(int i =length;i>0;i--) {
            //if heads cordinate are equal to the body part cordinate it means snake heads touch the body of snake
            if ((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
                flag = false;
            }
        }
        if(!flag){
            timer.stop();
            }
        }
    public void eat(){
            //if snake eat the food, snake length and score will increses
            if((xsnake[0]==foodx) && (ysnake[0]==foody)){
                length++;
                score++;
                spawnfood();
            }

    }
    public void move(){
        //updating all pody parts except head
        for(int i=length;i>0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }

        switch(dir){
            case'R':
                xsnake[0]=xsnake[0]+unit;//moving towards right so add 50
                break;
            case'L':
                xsnake[0]=xsnake[0]-unit;//moving towards left so subtract 50
                break;
            case'D':
                ysnake[0]=ysnake[0]+unit;//moving towards down so add 24
                break;
            case'U':
                ysnake[0]=ysnake[0]-unit;//moving towards up so subtract 24
                break;

        }
    }
    public class mykey extends KeyAdapter{
        public void keyPressed(KeyEvent evt){
            switch(evt.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(dir !='D'){//If current dir is UP, its not possible to change its side towards DOWN
                        dir ='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir !='U'){
                        dir ='D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir !='R'){
                        dir ='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir !='L'){
                        dir ='R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        score = 0;
                        length = 3;
                        dir = 'R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        gamestart();
                    }
                    break;
            }
        }
    }












    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            eat();
            checkhit();
        }
        repaint();

    }

}
