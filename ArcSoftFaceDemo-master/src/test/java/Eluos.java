

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
//纯java实现俄罗斯方块
public class Eluos extends JFrame{


    private Eluo_panel jPanel;

    private int this_width=500,this_height=500;
  public Eluos(){

      this.setSize(this_width, this_height);

      jPanel=new Eluo_panel();
      this.add(jPanel);


      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setVisible(true);

      this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("type");
                switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:

                    Eluos.this.jPanel.moveOther(Eluo_panel.MOVE_RIGHT, Eluos.this.jPanel.curt_xingzhuang);
                    break;
                case  KeyEvent.VK_RIGHT:

                    Eluos.this.jPanel.moveOther(Eluo_panel.MOVE_LEFT, Eluos.this.jPanel.curt_xingzhuang);
                    break;



                case KeyEvent.VK_UP:
                    System.out.println(Eluos.this.jPanel.curt_xingzhuang);
                Eluos.this.jPanel.curt_xingzhuang=
                Eluos.this.jPanel.bianXing(Eluos.this.jPanel.fangkuai.d, Eluos.this.jPanel.curt_xingzhuang);

                    break;
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {



            }
        });

  }

  public static void main(String[] args) {

      new Eluos();

}

}

class Eluo_panel extends JPanel implements Runnable{

     Fangkuai fangkuai;



     int huatu[][]=new int[20][20];
    long now_time=0;
    Random random=new Random();
    Color color=new Color(0);
  static final int MOVE_LEFT=1;
  static final int MOVE_RIGHT=2;

  boolean game_over=false;
   int curt_xingzhuang[][];
    public Eluo_panel(){

        fangkuai=createNewFangkui();

        Thread thread=new Thread(this);
        thread.start();


    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawBack(g);
        drawFangkui(g,curt_xingzhuang);
        moveDown(curt_xingzhuang);
    }

    /**
     * 画背景
     * @param g
     */
    void drawBack(Graphics g){


        for (int i = 0; i < huatu.length; i++) {
            for (int j = 0; j < huatu[i].length; j++) {
                if(huatu[i][j]!=0)
                    g.fillRect(j*20, i*20, Fangkuai.width-1,Fangkuai.height-1);
            }
        }
    }

    /**
     * 画一个方块
     * @param g
     * @param curt_xing
     */
     void drawFangkui(Graphics g,int curt_xing[][]){


         if(fangkuai==null)
         {
             fangkuai=createNewFangkui();

         }

        if(curt_xing!=null){
            int y=0;boolean b=false;
            for (int i = 0; i < curt_xing.length; i++) {
                for (int j = 0; j < curt_xing[i].length; j++) {
                    if(curt_xing[i][j]!=0)
                    {

                         g.setColor(fangkuai.getColor());
                        g.fillRect((fangkuai.run_x+j)*Fangkuai.width, (fangkuai.run_y+y)*Fangkuai.height,
                                Fangkuai.width-1, Fangkuai.height-1);
                        b=true;

                    }

                }
                if(b)
                    y++;


            }

        }
     }
    /**
     * 创建一个方块
     * @return
     */
    private Fangkuai createNewFangkui(){

        int index=0;
        Random random=new Random();
        Fangkuai fangkuai=new Fangkuai();
        Color color=new Color(random.nextInt(255),
                random.nextInt(255),random.nextInt(255));

        index=random.nextInt(4);
        fangkuai.setColor(color);
        curt_xingzhuang=Fangkuai.xingzhuangs[index];

        return fangkuai;
    }

    /**
     * 判断是否能够向下移动
     * @param xingzhuang
     * @return
     */
    boolean isCan_down(int xingzhuang[][]){


        int y=0;boolean b=false;
        for (int i = 0; i < xingzhuang.length; i++) {
            for (int j = 0; j < xingzhuang[i].length; j++) {
                if(xingzhuang[i][j]!=0)
                {
                    b=true;
                    if(fangkuai.run_y+y>=19||huatu[fangkuai.run_y+y+1][fangkuai.run_x+j]!=0){
                        return false;
                    }

                }

            }
            if(b)
                y++;

        }

        return true;
    }
    /**
     * 变形
     */

    public int[][] bianXing(int d,int arr[][]){

        if(arr==null||arr[0]==null)
            return null;

        int arr2[][]=new int[arr.length][arr[0].length];


        switch (d) {
        case 1:


            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    arr2[j][arr[i].length-1-i]=arr[i][j];
                }
            }


            break;

        default:
            break;
        }

        for (int i = 0; i < arr2.length; i++) {

            for (int j = 0; j < arr2[i].length; j++) {

                if(arr2[i][j]!=0)
                {
                if(fangkuai.run_x+j>19||fangkuai.run_y+i>19||fangkuai.run_x+i<0
                    ||huatu[fangkuai.run_y+i][fangkuai.run_x+j]!=0)
                    return arr;
                }
            }
        }

        return arr2;

    }
    /**
     * 向下移动
     * @param xingzhuang
     */
    private void moveDown(int xingzhuang[][]){

        if(isCan_down(xingzhuang))
            fangkuai.run_y++;

        else
        {

            /**
             * 如果不能向下移动就把当前方块的0和1 映射到整个面板上，重新创建一个方块
             */
            int y=0;boolean b=false;
            for (int i = 0; i < xingzhuang.length; i++) {
                for (int j = 0; j < xingzhuang[i].length; j++) {
                    if(xingzhuang[i][j]!=0)
                    {
                        huatu[fangkuai.run_y+y][fangkuai.run_x+j]=1;
                        b=true;
                    }

                }
                if(b)
                    y++;

            }


            xiaoChu();
            for (int i = 0; i < huatu[0].length; i++) {
                if(huatu[0][i]!=0)
                    game_over=true;
            }

            fangkuai=createNewFangkui();
        }

    }
    public void xiaoChu(){

        boolean xiao=false;

        for (int i = huatu.length-1; i >=0; i--) {

            xiao=false;
            int j=0;
            for ( j = 0; j < huatu[i].length; j++) {
                if(huatu[i][j]==0)
                    break;
            }

            if(j==huatu[i].length)
                xiao=true;


            if(xiao){

                for ( j = i; j >0; j--) {
                    for (int j2 = 0; j2 < huatu[j].length; j2++) {
                        huatu[j][j2]=huatu[j-1][j2];
                    }
                }
                for ( j = 0; j <huatu[0].length; j++) {
                    huatu[0][j]=0;
                }

            }

        }
    }
/**
 *  http://www.cnblogs.com/roucheng/
 * @param d
 * @param xingzhuang
 */
     void moveOther(int d,int xingzhuang[][]){

        int dx=d==MOVE_LEFT?1:-1;
        if(is_CanMoveOther(d, xingzhuang)){
            fangkuai.run_x+=dx;
        }
    }
    private boolean is_CanMoveOther(int d,int xingzhuang[][]){

        int dx=d==MOVE_LEFT?1:-1;
        int y=0;boolean has=false;
        for (int i = 0; i < xingzhuang.length; i++) {
            has=false;
            for (int j = 0; j < xingzhuang[i].length; j++) {

                if(xingzhuang[i][j]!=0)
                {
                    if(d==MOVE_LEFT&&fangkuai.run_x+j>=19||d==MOVE_RIGHT&&fangkuai.run_x+j<=0)

                            return false;
                    has=true;
                   if(huatu[fangkuai.run_y+y][fangkuai.run_x+j+dx]!=0){
                       return false;
                   }
                }
            }
            if(has)
                y++;
        }

        return true;
    }




    @Override
    public void run() {


      while(!game_over)
      {


          this.repaint();
          try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }
    }

}
class Fangkuai {



    private Color color;

     int run_x=10,run_y;

     int d=1;

     static final int width=20,height=20;

    public static final int  xingzhuangs[][][]={
        {{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
            {0,0,0,0,1,1,1,1},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},

            {{0,0,1,0},{0,1,1,1},{0,0,0,0},{0,0,0,0}},//土形
            {{0,0,0,0},{1,0,0,0},{1,1,0,0},{0,1,0,0}},
            {{1,1,1,1},{1,0,0,0},{0,0,0,0},{0,0,0,0}},//T形
            {{1,1},{1,1}}

    };

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRun_x() {
        return run_x;
    }

    public void setRun_x(int run_x) {
        this.run_x = run_x;
    }

    public int getRun_y() {
        return run_y;
    }

    public void setRun_y(int run_y) {
        this.run_y = run_y;
    }




}