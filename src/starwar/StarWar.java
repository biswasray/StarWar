/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starwar;

/**
 *
 * @author ACER
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
public class StarWar extends JFrame implements KeyListener {
private final MyPanel mp;
private int score,cb,runn=1;
int rx,ry;
long t1,t2,time;
String ship,bombstr;
public Sprite spr,temp,bomb;
public Random ran;
public ArrayList<Sprite> msl,osr,cmiss,omiss;
    public StarWar() {
        t1=System.currentTimeMillis();
        this.setTitle("Star war");
        this.setLayout(null);
        this.setSize(410, 630);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        ran=new Random();
        msl=new ArrayList<>();
        osr=new ArrayList<>();
        cmiss=new ArrayList<>();
        omiss=new ArrayList<>();
        mp=new MyPanel();
        mp.setBounds(0, 0, 400, 600);
        //this.add(mp);
        ship="/starwar/ship.png";
        spr=new Sprite(ship,177,480,mp);
       this.add(mp);
	this.setSize(410, 630);
	mp.repaint();
       }
    public static void main(String[] args) {
        StarWar star=new StarWar();
        
    }
    public void fire() {
        msl.add(new Sprite("/starwar/missile.png",spr.getX()+(spr.getW()/4),spr.getY()-31,mp));
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key=ke.getKeyCode();
    switch (key) {
        case KeyEvent.VK_DOWN:
            spr.moveD(25);
            break;
        case KeyEvent.VK_UP:
            spr.moveU(25);
            break;
        case KeyEvent.VK_RIGHT:
            spr.moveR(25);
            break;
        case KeyEvent.VK_LEFT:
            spr.moveL(25);
            break;
        case KeyEvent.VK_SPACE:
            fire();
            break;
        case KeyEvent.VK_ENTER:
            if(bombstr!=null) {
                for(Sprite obb:osr) {
                    score++;
                    obb.setImage("/starwar/crash.png");
                    omiss.add(obb);
                }
                bombstr=null;
                repaint();
            }
    }
        if(bomb!=null) {    
            if(bomb.isCollide(spr)) {
                    bomb=null;
                    bombstr="Press \"ENTER\" key to blast the bomb";
                }
        }
    for(Sprite obb : osr) {
      if((obb.isVisible)&&(obb.isCollide(spr))) {
                    //System.out.println("Down");
                    spr.setImage("/starwar/crash.png");
                    obb.setImage("/starwar/crash.png");
                    runn=0;
                    //repaint();
                    //spr.isVisible=false;
                    //obb.isVisible=false;  
                }  
    }
    repaint();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //repaint();
    }

    public class Stars {
        public int x,y;
        public Stars(int a,int b) {
        x=a;
        y=b;
        }
    }
    
public class MyPanel extends JPanel implements ActionListener {
private Image imo;
private Timer tm;
private boolean col,running=true;
private ArrayList<Stars> star,smiss;
public MyPanel() {
    star=new ArrayList<>();
    smiss=new ArrayList<>();
    for(int i=0;i<500;i++) {
        rx=0+(int)(Math.random()*400);
        ry=0+(int)(Math.random()*600);
        star.add(new Stars(rx,ry));
    }
    tm=new Timer(700,this);
    tm.start();
}
@Override
    public void paint(Graphics g) {
       g.setColor(Color.black);
       g.fillRect(0, 0, 400, 600);
       g.setColor(Color.white);
       for(int i=0;i<star.size();i++) {
         g.fillOval(star.get(i).x, star.get(i).y, 3, 3);
       }
       if(running) {
           if(runn==0) {
               running=false;
           }
       try {
           if(spr.isVisible) {
	imo=spr.getImage();
       g.drawImage(imo , spr.getX(), spr.getY(),this);
           }
           if(bomb!=null) {
             g.drawImage(bomb.getImage() , bomb.getX(), bomb.getY(),this);  
           }
       for(Sprite obb:osr) {
           if(obb.isVisible) {
	imo=obb.getImage();
       g.drawImage(imo , obb.getX(), obb.getY(),this); 
      }
       }
       for(int i=0;i<msl.size();i++) {
        if(msl.get(i).isVisible) {
	imo=msl.get(i).getImage();
       g.drawImage(imo , msl.get(i).getX(), msl.get(i).getY(),this); 
      }
       }
}
       catch(Exception e) {
           System.out.println("Game running");
       }
       g.setColor(Color.green);
       g.drawString("Score="+score, 10, 10);
       if(bombstr!=null) {
         g.setColor(Color.blue);
       g.drawString(bombstr, 100, 30);  
       }
       }
       else {
           tm.stop();
           g.setColor(Color.red);
           g.setFont(Font.getFont(Font.DIALOG));
           g.drawString("GAME OVER", 160, 300);
           g.setColor(Color.green);
           g.drawString("Score="+score, 160, 320);
       } 
      
  }   

        @Override
        public void actionPerformed(ActionEvent ae) {
            //repaint();
            t2=System.currentTimeMillis();
            for(int i=0;i<cmiss.size();i++) {
        msl.remove(cmiss.get(i));
        }
        for(int i=0;i<omiss.size();i++) {
        osr.remove(omiss.get(i));
        }
        for(int i=0;i<smiss.size();i++) {
        star.remove(smiss.get(i));
        }
        cmiss.clear();
        omiss.clear();
        smiss.clear();
         //repaint();
            cb=ran.nextInt(3);
            for(int i=0;i<10;i++) {
            rx=ran.nextInt(400);
            ry=ran.nextInt(10);
            star.add(new Stars(rx,ry));   
            }
            for(int i=0;i<star.size();i++) {
                star.get(i).y=star.get(i).y+10;
                if(star.get(i).y>=600) {
                    smiss.add(star.get(i));
                }
            }
           for(int i=0;i<cb;i++) {
        rx=ran.nextInt(400-36);
        temp=new Sprite("/starwar/obstacle.png",rx,0,mp);
              col = false;
        if(osr.size()==0) {
            osr.add(temp); 
        }
        else {
            for(int j=0;j<osr.size();j++) {
                if(osr.get(j).isCollide(temp)) {
                    col=true;
                }
            }
          if(col) {
                temp=null;
            }
            else {
            osr.add(temp);
            }  
        }
        if(t2-t1>15000&&bomb==null) {
            //System.out.println("check");
            while(bomb==null) {
            rx=ran.nextInt(400-36);
            bomb=new Sprite("/starwar/bomb.png",rx,0,mp);
            col=false;
            for(int j=0;j<osr.size();j++) {
                if(osr.get(j).isCollide(bomb)) {
                    col=true;
                }
            }
          if(col) {
                bomb=null;
            }
            }
            t1=System.currentTimeMillis();
        }
        //osr.add(temp);
            }
            for (Sprite msl1 : msl) {
                if(msl1.isVisible) {
                    msl1.moveU(30);
                }
                if(msl1.getY()<=0) {
                    cmiss.add(msl1);
                }
            }
            if(bomb!=null) {
                bomb.moveD(20);
                if(bomb.isCollide(spr)) {
                    bomb=null;
                    bombstr="Press \"ENTER\" key to blast the bomb";
                }
                else if(bomb.getY()>=560) {
                    bomb=null;
                }
            }
            for(Sprite obb:osr) {
                obb.moveD(20);
                if((obb.isVisible)&&(obb.isCollide(spr))) {
                    System.out.println("Down");
                    spr.setImage("/starwar/crash.png");
                    obb.setImage("/starwar/crash.png");
                    runn=0;
                    repaint();
                    //spr.isVisible=false;
                    //obb.isVisible=false;  
                }
               if(obb.getY()>=560) {
                    omiss.add(obb);
                } 
            }
        for(Sprite obb:osr) {   
            for (Sprite msl1 : msl) {
                 if((obb.isVisible)&&(obb.isCollide(msl1))) {
                     try {
                         score++;
                  obb.setImage("/starwar/crash.png");
                  msl1.setImage("/starwar/crash.png");
                  repaint();
                  //repaint();
                  //Thread.sleep(1000);
                  //obb.isVisible=false;
                  //obb.setBound(0,0,1,1);
                  omiss.add(obb);
                  //msl1.isVisible=false;
                  //msl1.setBound(0, 0, 0, 0);
                  cmiss.add(msl1);
                 }
                catch(Exception e) {
                    System.out.println("boom");
                }    
                 }      
                }
        }
           repaint();
        }
    }
    
public class Sprite {
 private int x,y,w,h;
 private Image im;
 private Rectangle rp;
 public boolean isVisible=true;
 public Sprite(String st,int a,int b,JPanel jp) {
 this.x=a;
 this.y=b;
 InputStream res=StarWar.class.getResourceAsStream(st);
     try {
         im=ImageIO.read(res);
     } catch (IOException ex) {
         this.im=(new ImageIcon(st)).getImage();
     }
   /*  try {
         im=ImageIO.read(new File(st));
     } catch (IOException ex) {
         Logger.getLogger(StarWar.class.getName()).log(Level.SEVERE, null, ex);
     }*/
 rp=new Rectangle(x,y,im.getWidth(null),im.getHeight(null));
 w=im.getWidth(null);
 h=im.getHeight(null);
 }
 public void moveR(int q) {
     if(x+im.getWidth(null)<400) { 
     x=x+q;
     rp.setRect(x, y,im.getWidth(null),im.getHeight(null));
 }
 }
public void moveL(int q) {
    if(x>0) { 
    x=x-q;
     rp.setRect(x, y,im.getWidth(null),im.getHeight(null));
 }
}
public void moveU(int q) {
    if(y>0) {
    y=y-q;
     rp.setRect(x, y,im.getWidth(null),im.getHeight(null));
 }
}
public void moveD(int q) {
    if(y+im.getHeight(null)<600) { 
    y=y+q;
     rp.setRect(x, y,im.getWidth(null),im.getHeight(null));
 }
}
public Rectangle getBound() {
    return rp.getBounds();
}
public void setBound(int a,int b,int c,int d) {
   rp.setRect(a,b,c,d); 
}
public boolean isCollide(Sprite temp) {
         if(rp.intersects(temp.getBound())) {
         return true;
     }
     else {
         return false;
     }
}
public void setImage(String io) {
    InputStream res=StarWar.class.getResourceAsStream(io);
     try {
         im=ImageIO.read(res);
     } catch (IOException ex) {
         this.im=(new ImageIcon(io)).getImage();
     }
}
public Image getImage() {
    return im;
}
public int getX() {
    return x;
}
public int getY() {
    return y;
}
public int getW() {
    return w;
}
public int getH() {
    return h;
}
public void setX(int a) {
    this.x=a;
}
public void setY(int b) {
    this.y=b;
}
public void setW(int a) {
    this.w=a;
}
public void setH(int b) {
    this.h=b;
}
  }
}