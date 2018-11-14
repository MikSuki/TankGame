import java.util.Vector;

import jaco.mp3.player.MP3Player;


import java.io.*;


//音樂

class playMusic {

	FileInputStream fis = null;
	BufferedInputStream bis = null;
	
	public void startMusic() {
		
		new MP3Player(new File("startMusic.mp3")).play();
	}

	
	public void startBoom() {
		
		File file = new File("Boom2.mp3");
		MP3Player mp3player = new MP3Player(file);
		mp3player.play();
		
	}
}

	// 載入位置

	class Location {
		int x = 0;
		int y = 0;
		int direction = 0;

		public Location(int x, int y, int direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}
	}

	// 記錄

	class Record {

		// 記錄敵人數
		private static int enemyNum = 7;
		// 記錄我的生命
		private static int mylive = 3;
		// 記錄消滅敵人數
		private static int killEnemyNum = 0;

		private static FileWriter fw = null;
		private static BufferedWriter bw = null;
		private static FileReader fr = null;
		private static BufferedReader br = null;

		private EnemyTank et = null;
		private static Vector<EnemyTank> ets = null;

		private Location location = null;
		private static Vector<Location> locations = new Vector<>();

		// get、set

		public static Vector<EnemyTank> getEts() {
			return ets;
		}

		public static void setEts(Vector<EnemyTank> ets) {
			Record.ets = ets;
		}

		public static int getEnemyNum() {
			return enemyNum;
		}

		public static void setEnemyNum(int enemyNum) {
			Record.enemyNum = enemyNum;
		}

		public static int getMylive() {
			return mylive;
		}

		public static void setMylive(int mylive) {
			Record.mylive = mylive;
		}

		public static int getKillEnemyNum() {
			return killEnemyNum;
		}

		public static void setKillEnemy(int killEnemy) {
			Record.killEnemyNum = killEnemy;
		}

		// 改變變量
		public static void reduceEnemyNum() {
			enemyNum--;
		}

		public static void reduceLive() {
			mylive--;
		}

		public static void reduceKillEnemyNum() {
			killEnemyNum++;
		}

		// 寫入最高紀錄
		public static void saveRecord() {

			try {
				fw = new FileWriter("kill_record.txt");
				bw = new BufferedWriter(fw);

				bw.write(killEnemyNum + "\r\n");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 寫入位置
		public static void save() {

			try {

				fw = new FileWriter("location.txt");
				bw = new BufferedWriter(fw);

				for (int i = 0; i < ets.size(); i++) {
					EnemyTank et = ets.get(i);

					if (et.live == true) {

						String s = et.x + " " + et.y + " " + et.direction;
						bw.write(s + "\r\n");

					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 讀取總紀錄
		public static void readAllRecord() {

			try {
				fr = new FileReader("kill_record.txt");
				br = new BufferedReader(fr);

				String s = br.readLine();
				killEnemyNum = Integer.parseInt(s);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// 載入位置

		public static Vector<Location> load() {

			try {
				fr = new FileReader("location.txt");
				br = new BufferedReader(fr);

				String s = "";
				while ((s = br.readLine()) != null) {

					String[] xyd = s.split(" ");

					Location location = new Location(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
							Integer.parseInt(xyd[2]));

					locations.add(location);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return locations;

		}

	}

	// 爆炸

	class Boom {
		int x = 0;
		int y = 0;
		int time = 9;

		public Boom(int x, int y) {
			this.x = x;
			this.y = y;

		}

		public void liveDown() {
			if (time > 0)
				time--;

		}

	}

	// 子彈類
	class Bullet implements Runnable {
		int x = 0;
		int y = 0;
		int speed = 25;
		int direction = 0;
		// 子彈存活
		Boolean live = true;

		public Bullet(int x, int y, int direction) {
			switch (direction) {
			// 子彈位置
			case 0:
				this.x = x - 10;
				this.y = y - 7;
				break;

			case 1:
				this.x = x - 15;
				this.y = y - 10;
				break;

			case 2:
				this.x = x - 10;
				this.y = y - 17;
				break;

			case 3:
				this.x = x - 10;
				this.y = y - 10;
				break;
			}

			this.direction = direction;

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				switch (direction) {
				case 0:
					y -= speed;
					break;
				case 1:
					x += speed;
					break;
				case 2:
					y += speed;
					break;
				case 3:
					x -= speed;
					break;
				}
				if (x < 0 || y < 0 || x > 800 || y > 600)
					live = false;

			}
		}
	}

	// 坦克類
	class Tank {
		// 定義坦克生命數
		boolean live = true;

		// up:0 right:1 down:2 left:3
		int direction = 0;

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		int speed = 20;
		int x = 0;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		int y = 0;

		public Tank(int x, int y) {
			this.x = x;
			this.y = y;

		}
	}

	// 我的坦克
	class MyTank extends Tank {

		// 子彈
		Vector<Bullet> blts = new Vector<Bullet>();
		Bullet blt = null;

		public MyTank(int x, int y) {
			// =Tank(x,y);
			super(x, y);
		}

		// 移動方法
		public void up() {
			if (y > 40)
				y -= speed;
			direction = 0;
		}

		public void right() {
			if (x < 730)
				x += speed;
			direction = 1;
		}

		public void down() {
			if (y < 540)
				y += speed;
			direction = 2;
		}

		public void left() {
			if (x > 50)
				x -= speed;
			direction = 3;
		}

		// 發射子彈方法
		public void shot() {

			switch (direction) {

			case 0:
				blt = new Bullet(x + 7, y - 30, 0);
				blts.add(blt);
				break;
			case 1:
				blt = new Bullet(x + 50, y + 7, 1);
				blts.add(blt);
				break;
			case 2:
				blt = new Bullet(x + 7, y + 50, 2);
				blts.add(blt);
				break;
			case 3:
				blt = new Bullet(x - 30, y + 7, 3);
				blts.add(blt);
				break;

			}
			Thread myblt = new Thread(blt);
			myblt.start();

		}
	}

	// 敵人坦克
class EnemyTank extends Tank implements Runnable {

	boolean Go = true;

	// 敵人坦克
	Vector<EnemyTank> ets = new Vector<EnemyTank>();

	// 敵人子彈
	Vector<Bullet> blts = new Vector<>();
	Bullet blt = null;

	public EnemyTank(int x, int y, int direction) {
		super(x, y);
		this.direction = direction;
		// TODO Auto-generated constructor stub
	}

	public void setEts(Vector ets) {
		this.ets = ets;
	}

	// 判斷敵人坦克會不會重疊
	public boolean canGo() {

		switch (this.direction) {

		case 0:
			for (int i = 0; i < ets.size(); i++) {

				EnemyTank et = ets.get(i);

				if (et != this) {

					if ((et.direction == 0) || (et.direction == 2)) {

						if (this.x - 20 >= et.x - 20 && this.x - 20 <= et.x + 20 && this.y - 26 - speed >= et.y - 26
								&& this.y - 26 - speed <= et.y + 26) {
							return true;
						}
						if (this.x + 20 >= et.x - 20 && this.x + 20 <= et.x + 20 && this.y - 26 - speed >= et.y - 26
								&& this.y - 26 - speed <= et.y + 26) {
							return true;
						}

					}
					if ((et.direction == 1) || (et.direction == 3)) {

						if (this.x - 20 >= et.x - 26 && this.x - 20 <= et.x + 26 && this.y - 26 - speed >= et.y - 20
								&& this.y - 26 - speed <= et.y + 20) {
							return true;
						}
						if (this.x + 20 >= et.x - 26 && this.x + 20 <= et.x + 26 && this.y - 26 - speed >= et.y - 20
								&& this.y - 26 - speed <= et.y + 20) {
							return true;
						}

					}
				}
			}

			break;

		case 1:
			for (int i = 0; i < ets.size(); i++) {

				if (this != ets.get(i)) {

					EnemyTank et = ets.get(i);

					if ((et.direction == 0) || (et.direction == 2)) {

						if (this.y - 20 >= et.y - 26 && this.y - 20 <= et.y + 26 && this.x + 26 + speed >= et.x - 20
								&& this.x + 26 + speed <= et.x + 20) {
							return true;
						}
						if (this.y + 20 >= et.y - 26 && this.y + 20 <= et.y + 26 && this.x + 26 + speed >= et.x - 20
								&& this.x + 26 + speed < et.x + 20) {
							return true;
						}
					}
					if ((et.direction == 1) || (et.direction == 3)) {

						if (this.y - 20 >= et.y - 20 && this.y - 20 <= et.y + 20 && this.x + 26 + speed >= et.x - 26
								&& this.x + speed <= et.x + 20) {
							return true;
						}
						if (this.y + 20 >= et.y - 20 && this.y + 20 <= et.y + 20 && this.x + 26 + speed >= et.x - 26
								&& this.x + 26 + speed <= et.x + 26) {
							return true;
						}

					}
				}
			}

			break;

		case 2:
			for (int i = 0; i < ets.size(); i++) {

				if (this != ets.get(i)) {

					EnemyTank et = ets.get(i);

					if ((et.direction == 0) || (et.direction == 2))

						if (this.x - 20 >= et.x - 20 && this.x - 20 <= et.x + 20 && this.y + 26 + speed >= et.y - 26
								&& this.y + 26 + speed <= et.y + 26) {
							return true;
						}
					if (this.x + 20 >= et.x - 20 && this.x + 20 <= et.x + 20 && this.y + 26 + speed >= et.y - 26
							&& this.y + 26 + speed <= et.y + 26) {
						return true;
					}

					if ((et.direction == 1) || (et.direction == 3)) {

						if (this.x - 20 >= et.x - 26 && this.x - 20 <= et.x + 26 && this.y + 26 + speed >= et.y - 20
								&& this.y + 26 + speed <= et.y + 20) {
							return true;
						}
						if (this.x + 20 >= et.x - 26 && this.x + 20 <= et.x + 26 && this.y + 26 + speed >= et.y - 20
								&& this.y + 26 + speed <= et.y + 20) {
							return true;
						}

					}
				}
			}

			break;

		case 3:
			for (int i = 0; i < ets.size(); i++) {

				if (this != ets.get(i)) {

					EnemyTank et = ets.get(i);

					if ((et.direction == 0) || (et.direction == 2)) {

						if (this.y - 20 >= et.y - 26 && this.y - 20 <= et.y + 26 && this.x - 26 - speed >= et.x - 20
								&& this.x - 26 - speed <= et.x + 20) {
							return true;
						}
						if (this.y + 20 >= et.y - 26 && this.y + 20 <= et.y + 26 && this.x - 26 - speed >= et.x - 20
								&& this.x - 26 - speed < et.x + 20) {
							return true;
						}
					}
					if ((et.direction == 1) || (et.direction == 3)) {

						if (this.y - 20 >= et.y - 20 && this.y - 20 <= et.y + 20 && this.x - 26 - speed >= et.x - 26
								&& this.x - 26 - speed <= et.x + 20) {
							return true;
						}
						if (this.y + 20 >= et.y - 20 && this.y + 20 <= et.y + 20 && this.x - 26 - speed >= et.x - 26
								&& this.x - 26 - speed <= et.x + 26) {
							return true;
						}

					}
				}
			}

			break;

		}

		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 隨機移動
			switch (direction) {

			case 0:

				for (int i = 0; i < (int) (Math.random() * 10); i++) {

					if (y - 25 < 20 || this.canGo())
						break;

					y -= speed;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 1:
				for (int i = 0; i < (int) (Math.random() * 10); i++) {
					if (x + 25 > 700 || this.canGo())
						break;
					x += speed;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 2:
				for (int i = 0; i < (int) (Math.random() * 10); i++) {
					if (y + 25 > 570 || this.canGo())
						break;
					y += speed;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 3:
				for (int i = 0; i < (int) (Math.random() * 10); i++) {
					if (x - 25 < 20 || this.canGo())
						break;
					x -= speed;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}

			// 發射子彈

			if (blts.size() < 1) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch (direction) {

				case 0:

					blt = new Bullet(x + 7, y - 30, 0);
					blts.add(blt);
					break;

				case 1:
					blt = new Bullet(x + 50, y + 7, 1);
					blts.add(blt);
					break;

				case 2:
					blt = new Bullet(x + 7, y + 50, 2);
					blts.add(blt);
					break;

				case 3:
					blt = new Bullet(x - 30, y + 7, 3);
					blts.add(blt);
					break;

				}
				Thread enemyblt = new Thread(blt);
				enemyblt.start();

			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			direction = (int) (Math.random() * 4);

		}

	}

}
