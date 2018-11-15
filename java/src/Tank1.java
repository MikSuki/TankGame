
//坦克大戰
import java.util.*;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import java.awt.*;
import javax.imageio.*;

public class Tank1 extends JFrame implements ActionListener{

	StartPanel sp = null;
	ChoosePanel cp = null;
	GamePanel gp = null;
	JMenuBar jmb = null;
	JMenu jm1 = null;
	JMenuItem jmi1, jmi2, jmi3, jmi4 = null;
	JButton jb0,jb1,jb2,jb3,jb4,jb5,jb6,jb11 = null;
	JPanel jp0,jp1,jp2,jp3,jp4,jp5;
	JLabel jl1 = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tank1 tank1 = new Tank1();
	}

class people{
	int year,height;
	people(){
		
	}
}
	// 構造函數
	public Tank1() {
		Record.readAllRecord();
		sp = new StartPanel();

		jmb = new JMenuBar();
		jm1 = new JMenu("選項(O)");
		jm1.setMnemonic('O');

		jmi1 = new JMenuItem("開始遊戲(G)"/* ,new ImageIcon(" .jpg") */);
		jmi1.setMnemonic('G');
		jmi2 = new JMenuItem("繼續遊戲(C)"/* ,new ImageIcon(" .jpg") */);
		jmi2.setMnemonic('C');
		jmi3 = new JMenuItem("保存遊戲(S)"/* ,new ImageIcon(" .jpg") */);
		jmi3.setMnemonic('S');
		jmi4 = new JMenuItem("離開遊戲(E)"/* ,new ImageIcon(" .jpg") */);
		jmi4.setMnemonic('E');
		
		Font fontb = new Font("SansSerif",Font.BOLD,20);
		jb0 = new JButton("開始遊戲");
		jb0.setFont(fontb);
		jb0.setBounds(500, 400, 150, 50);
		
		jb11 = new JButton("選擇關卡");
		jb11.setFont(fontb);
		jb11.setBounds(200, 400, 150, 50);
		

		jmb.add(jm1);
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);

		jmi1.addActionListener(this);
		jmi1.setActionCommand("start");
		
		jmi2.addActionListener(this);
		jmi2.setActionCommand("continue");


		jmi4.addActionListener(this);
		jmi4.setActionCommand("leave");
		
		jb0.addActionListener(this);
		jb0.setActionCommand("start");
		
		jb11.addActionListener(this);
		jb11.setActionCommand("choose");
		
		sp.add(jb0);
		sp.add(jb11);
		
		sp.setBackground(Color.black);
		sp.setLayout (null);
		this.setContentPane(sp); 
		
		Thread t = new Thread(sp);
		t.start();
		this.setJMenuBar(jmb);

		this.setTitle("Tank Game");

		this.setSize(1000, 850);
		
		//this.setLocation(300, 0);
		this.setLocationRelativeTo(null); 
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// 按下選項
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		// 開始遊戲
		if (arg0.getActionCommand().equals("start")) {
			
			jmi3.addActionListener(this);
			jmi3.setActionCommand("save");
			
			gp = new GamePanel("newGame");
			Thread t = new Thread(gp);
			t.start();
			
			this.setContentPane(gp); 
			this.requestFocusInWindow();
			this.addKeyListener(gp);
			this.setVisible(true);
			

		}

		// 離開遊戲
		if (arg0.getActionCommand().equals("leave")) {

			Record.saveRecord();

			System.exit(0);

		}

		// 保存遊戲
		if (arg0.getActionCommand().equals("save")) {

			Record record = new Record();
			Record.saveRecord();
			Record.setEts(gp.ets);
			Record.save();

		}

		// 繼續遊戲
		if (arg0.getActionCommand().equals("continue")) {

			gp = new GamePanel("continueGame");
			Thread t = new Thread(gp);
			t.start();
			this.requestFocusInWindow();
			this.addKeyListener(gp);

			this.setContentPane(gp);
			this.setVisible(true);

		}
		
		if(arg0.getActionCommand().equals("choose")) {
			
			ChoosePanel cp = new ChoosePanel();
			
			Font font = new Font("標楷體",Font.BOLD,25);
			
			jb1 = new JButton("1");
			jb2 = new JButton("2");
			jb3 = new JButton("3");
			jb4 = new JButton("4");
			jb5 = new JButton("5");
			jb6 = new JButton("6");
			jb1.setFont(font);
			jb2.setFont(font);
			jb3.setFont(font);
			jb4.setFont(font);
			jb5.setFont(font);
			jb6.setFont(font);
			
			jb1.addActionListener(this);
			jb1.setActionCommand("start");

			font = new Font("標楷體",Font.BOLD,50);
			jl1 = new JLabel("選擇關卡");
			jl1.setFont(font);
			
			GridLayout grid1 = new GridLayout(4,0,10,10);
			GridLayout grid2 = new GridLayout(0,3);
			
			jp0 = new JPanel();
			jp1 = new JPanel();
			jp2 = new JPanel();
			jp5 = new JPanel();
			
			jp0.add(jl1);
			
			jp1.setLayout(grid2);
			jp1.add(jb1);
			jp1.add(jb2);
			jp1.add(jb3);
			
			jp2.setLayout(grid2);
			jp2.add(jb4);
			jp2.add(jb5);
			jp2.add(jb6);
			
			this.setContentPane(cp);
			
			cp.setLayout(grid1);
			cp.add(jp0);
			cp.add(jp1);
			cp.add(jp2);
			
			this.setVisible(true);
			
		}

	}

}

class StartPanel extends JPanel implements Runnable {
	
	Image image2 = null;
	Image image3 = null;
	Image image4 = null;
	
	public StartPanel() {
		
		try {
			image2 =ImageIO.read(new File("Tank.jpg"));
			image3 =ImageIO.read(new File("Tank2.jpg"));
			image4 =ImageIO.read(new File("plane.jpg"));
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {

		super.paint(g);
		/*g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 850);*/


		Font font = new Font("Dialog", Font.BOLD, 50);
		g.setFont(font);
		g.setColor(Color.red);
		g.drawString("T", 250, 250);
		g.setColor(Color.orange);
		g.drawString("A", 300, 250);
		g.setColor(Color.yellow);
		g.drawString("N", 350, 250);
		g.setColor(Color.green);
		g.drawString("K", 400, 250);
		g.setColor(Color.cyan);
		g.drawString("B a t t l e", 350, 320);
		
		g.drawImage(image2, 500, 450, 400,300, this);
		g.drawImage(image3, 100, 500, 300,130, this);
		g.drawImage(image4, 500, 100, 300,130, this);
	}
	

	// 閃爍
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}

class ChoosePanel extends JPanel {
	
	
	public void paint(Graphics g) {
		
		super.paint(g);
		


	}
	
	
	
	
}

class GamePanel extends JPanel implements KeyListener, Runnable {
	// 定義我的坦克(成員變量)
	MyTank mytank = null;
	// 子彈
	Bullet blt = null;
	// 敵人
	int etsize = 7;
	EnemyTank et = null;
	Vector<EnemyTank> ets = new Vector<>();
	// 爆炸
	Boom bm = null;
	Vector<Boom> booms = new Vector<>();
	// 圖片
	Image image1 = null;
	// 載入位置
	Location location = null;
	Vector<Location> locations = null;
	// 音效
	playMusic music = new playMusic();

	// 構造函數
	public GamePanel(String flag) {
		mytank = new MyTank(300, 300);

		// 創建敵人坦克

		if (flag.equals("newGame")) {

			for (int i = 0; i < etsize; i++) {

				// 創建坦克
				EnemyTank et = new EnemyTank((i + 1) * 100, 100, 2);

				// 加入vector
				ets.add(et);
				et.setEts(ets);

				// 敵人開始移動
				Thread t = new Thread(et);
				t.start();

			}
			music.startMusic();

		} else if (flag.equalsIgnoreCase("continueGame")) {

			locations = Record.load();

			for (int i = 0; i < locations.size(); i++) {

				// 創建坦克
				Location location = locations.get(i);
				EnemyTank et = new EnemyTank(location.x, location.y, location.direction);

				// 加入vector
				ets.add(et);
				et.setEts(ets);

				// 敵人開始移動
				Thread t = new Thread(et);
				t.start();

			}
			music.startMusic();
		}

		try {
			image1 = ImageIO.read(new File("Boom.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 初始畫面
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);

		g.setColor(Color.gray);
		g.fillRect(800, 0, 200, 600);
		g.fillRect(0, 600, 1000, 250);

		// 畫自己的坦克

		if (mytank.live == true) {
			this.drawTank(mytank.getX(), mytank.getY(), g, mytank.getDirection(), 0);
		}

		// 畫敵人坦克
		for (int i = 0; i < ets.size(); i++) {
			EnemyTank et = ets.get(i);

			// ets.get(i) -> 第i台坦克
			if (et.live == true) {

				this.drawTank(et.x, et.y, g, et.direction, 1);

				for (int j = 0; j < et.blts.size(); j++) {
					Bullet blt = et.blts.get(j);

					if (blt.live == true) {
						switch (et.direction) {

						case 0:
						case 2:
							g.fillOval(blt.x + 1, blt.y, 5, 5);

						case 1:
						case 3:
							g.fillOval(blt.x, blt.y + 1, 5, 5);

						}

					} else {
						et.blts.remove(blt);
					}

				}

			}

		}

		// 畫自己的子彈
		g.setColor(Color.RED);
		for (int i = 0; i < mytank.blts.size(); i++) {
			// mytank.ss.get(i) —> 當前子彈
			Bullet blt = mytank.blts.get(i);
			if (blt.live == true) {

				switch (mytank.direction) {

				case 0:
				case 2:
					g.fillOval(blt.x + 1, blt.y, 5, 5);

				case 1:
				case 3:
					g.fillOval(blt.x, blt.y + 1, 5, 5);

				}

			}
			// 移除子彈
			if (blt.live == false)
				mytank.blts.remove(blt);
		}

		// 畫出爆炸

		for (int i = 0; i < booms.size(); i++) {

			Boom bm = this.booms.get(i);

			if ((bm.time > 7) || (bm.time <= 5 && bm.time > 3)) {
				g.drawImage(image1, bm.x, bm.y, 50, 50, this);
			} else if ((bm.time <= 7 && bm.time > 5) || (bm.time > 0 && bm.time <= 3)) {
				g.drawImage(image1, bm.x, bm.y, 40, 40, this);
			}
			bm.liveDown();

			if (bm.time == 0)
				booms.remove(bm);

		}
		// 提示訊息
		drawInfo(g);

	}

	// 畫提示訊息方法
	public void drawInfo(Graphics g) {

		Font font = new Font("Dialog", Font.BOLD, 20);
		g.setFont(font);

		drawTank(200, 680, g, 0, 1);
		g.drawString(Record.getEnemyNum() + "", 240, 690);

		drawTank(300, 680, g, 0, 0);
		g.drawString(Record.getMylive() + "", 340, 690);

		font = new Font("Dialog", Font.BOLD, 28);
		g.setFont(font);

		g.setColor(Color.red);
		g.drawString("總殺敵數：" + Record.getKillEnemyNum(), 800, 300);

	}

	// 畫坦克方法
	public void drawTank(int x, int y, Graphics g, int direction, int type) {
		switch (type) {
		case 0:
			g.setColor(Color.cyan);
			break;

		case 1:
			g.setColor(Color.GREEN);
			break;
		}

		switch (direction) {
		case 0:
			// 左矩形
			g.fill3DRect(x - 20, y - 25, 10, 52, false);
			// 右矩形
			g.fill3DRect(x + 10, y - 25, 10, 52, false);
			// 中間矩形
			g.fill3DRect(x - 12, y - 15, 30, 32, false);
			// 圓形
			g.fillOval(x - 10, y - 10, 20, 20);
			// 砲管
			g.drawLine(x, y, x, y - 30);
			break;

		case 1:
			// 左矩形
			g.fill3DRect(x - 25, y - 20, 52, 10, false);
			// 右矩形
			g.fill3DRect(x - 25, y + 10, 52, 10, false);
			// 中間矩形
			g.fill3DRect(x - 15, y - 12, 32, 30, false);
			// 圓形
			g.fillOval(x - 10, y - 10, 20, 20);
			// 砲管
			g.drawLine(x, y, x + 32, y);
			break;

		case 2:
			// 左矩形
			g.fill3DRect(x - 20, y - 25, 10, 52, false);
			// 右矩形
			g.fill3DRect(x + 10, y - 25, 10, 52, false);
			// 中間矩形
			g.fill3DRect(x - 12, y - 15, 30, 32, false);
			// 圓形
			g.fillOval(x - 10, y - 10, 20, 20);
			// 砲管
			g.drawLine(x, y, x, y + 32);
			break;

		case 3:
			// 左矩形
			g.fill3DRect(x - 25, y - 20, 52, 10, false);
			// 右矩形
			g.fill3DRect(x - 25, y + 10, 52, 10, false);
			// 中間矩形
			g.fill3DRect(x - 15, y - 12, 32, 30, false);
			// 圓形
			g.fillOval(x - 10, y - 10, 20, 20);
			// 砲管
			g.drawLine(x, y, x - 32, y);
			break;
		}

	}

	// 判斷我是否擊中敵人
	public void shotEnemy() {
		for (int i = 0; i < mytank.blts.size(); i++) {
			Bullet blt = mytank.blts.get(i);
			if (blt.live == true) {

				for (int j = 0; j < ets.size(); j++) {

					EnemyTank et = ets.get(j);

					if (et.live == true && blt.live == true) {
						if (this.aceTank(blt, et)) {
							Record.reduceEnemyNum();
							Record.reduceKillEnemyNum();
						}
					}

				}
			}

		}
	}

	// 判斷敵人是否擊中我
	public void shotMytank() {

		for (int i = 0; i < ets.size(); i++) {

			EnemyTank et = ets.get(i);

			for (int j = 0; j < et.blts.size(); j++) {

				Bullet blt = et.blts.get(j);

				if (mytank.live == true && blt.live == true) {
					this.aceTank(blt, mytank);
				}

			}

		}

	}

	// 消滅坦克方法

	public boolean aceTank(Bullet blt, Tank tank) {

		boolean b = false;

		switch (tank.direction) {
		case 0:
		case 2:
			if (blt.x >= (tank.x - 20) && blt.x <= (tank.x + 20) && blt.y >= (tank.y - 20) && blt.y <= (tank.y + 20)) {
				tank.live = false;
				blt.live = false;

				b = true;

				// 建立爆炸
				Boom bm = new Boom(tank.x - 22, tank.y - 22);
				this.music.startBoom();
				booms.add(bm);

			}
			break;
		case 1:
		case 3:
			if (blt.x >= (tank.x - 25) && blt.x <= (tank.x + 25) && blt.y >= (tank.y - 20) && blt.y <= (tank.y + 20)) {
				tank.live = false;
				blt.live = false;

				b = true;

				// 建立爆炸
				Boom bm = new Boom(tank.x - 22, tank.y - 22);
				booms.add(bm);

			}
			break;

		}
		return b;

	}

	// 鍵盤事件
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		// 移動
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.mytank.down();
			}
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.mytank.up();
			}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.mytank.left();
			}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.mytank.right();
			}

		this.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		// 攻擊
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			if (this.mytank.blts.size() <= 5 && mytank.live == true)
				this.mytank.shot();

		}
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.shotEnemy();
			this.shotMytank();

			this.repaint();
		}
	}


}
