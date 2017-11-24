package dogfighters;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8338425678359909579L;
	public static final int WIDTH = 600;	//面板宽
	public static final int HEIGHT = 500;	//面板高
	
	/*游戏当前状态：START, RUNNING, PAUSE, GAME_OVER*/
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	
	private int score = 0;	//初始得分
	private Timer timer;	//定时器
	private int interval = 1000 / 100;	//设置时间间隔（毫秒）
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage enemyplane;
	public static BufferedImage star;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage iconimage;
	
	private FlyingObject[] flyings = {};	//敌机数组
	private Bullet[] bullets = {};	//子弹数组
	private Hero hero = new Hero();	//英雄机
	
	static {	//静态代码块
		try {
			background = ImageIO.read(ShootGame.class.getResource("/images/background.jpg"));
			start = ImageIO.read(ShootGame.class.getResource("/images/start.jpg"));
			enemyplane = ImageIO.read(ShootGame.class.getResource("/images/enemy.png"));
			star = ImageIO.read(ShootGame.class.getResource("/images/star.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("/images/bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("/images/hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("/images/hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("/images/pause.jpg"));
			gameover = ImageIO.read(ShootGame.class.getResource("/images/gameover.jpg"));
			iconimage = ImageIO.read(ShootGame.class.getResource("/images/icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*画*/
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);	//画背景图
		paintHero(g);	//画英雄机
		paintBullets(g);	//画子弹
		paintFlyingObjects(g);	//画飞行物
		paintScore(g);	//画分数
		paintState(g);	//画游戏状态
	}
	
	/*画英雄机*/
	public void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}
	
	/*画子弹*/
	public void paintBullets(Graphics g) {
		for(int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(), null);
		}
	}
	
	/*画飞行物*/
	public void paintFlyingObjects(Graphics g) {
		for(int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}
	
	public void paintScore(Graphics g) {
		int x = 10; //x坐标
		int y = 25;	//y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);	//字体
		g.setColor(new Color(0xFF0000));
		g.setFont(font);	//设置字体
		g.drawString("SCORE:" + score, x, y);	//画分数
		y = y + 20;	//y坐标增加20
		g.drawString("LIFE:" + hero.getLife(), x, y);	//画生命值
	}
	
	public void paintState(Graphics g) {
		switch (state) {
		case START:	//启动状态
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:	//暂停状态
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:	//游戏结束状态
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("DogFighters");
		ShootGame game = new ShootGame();	//面板对象
		frame.add(game);	//将面板添加到JFrame当中
		frame.setSize(WIDTH, HEIGHT);	//设置面板大小
		frame.setAlwaysOnTop(true);	//设置面板总是在最上方
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置关闭窗口方式为默认
		frame.setIconImage(iconimage);	//设置窗体图标
		frame.setLocationRelativeTo(null);	//设置窗体初始位置
		frame.setVisible(true);	//尽快调用paint
		
		game.action();	//启动执行
	}
	
	/*启动执行*/
	public void action() {
		//鼠标监听事件
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {	//鼠标移动
				if(state == RUNNING) {	//运行状态下移动鼠标等于移动英雄机
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {	//鼠标进入
				if(state == PAUSE) {	//暂停状态下运行
					state = RUNNING;
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {	//鼠标退出
				if(state == RUNNING) {	//游戏未结束，则设置为暂停
					state = PAUSE;
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {	//鼠标点击
				switch (state) {
				case START:
					state = RUNNING;	//启动状态下运行
					break;
				case GAME_OVER:
					flyings = new FlyingObject[0];	//清空飞行物
					bullets = new Bullet[0];	//清空子弹
					hero = new Hero();	//重新创建英雄机
					score = 0;	//分数清零
					state = START;	//状态设置为启动
					break;
				}
			}
		};
		this.addMouseListener(l);	//处理鼠标点击操作
		this.addMouseMotionListener(l);	//处理鼠标滑动操作
		
		timer = new Timer();	//主流程（时间）控制
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(state == RUNNING) {	//运行状态
					enterAction();	//飞行物入场
					stepAction();	//走一步
					shootAction();	//英雄机射击
					bangAction();	//子弹击中飞行物
					outOfBoundsAction();	//删除越界飞机物及子弹
					checkGameOverAction();	//检查游戏是否结束
				}
				repaint();	//重绘，调用paint()方法
			}
		}, interval, interval);
	}
	
	int flyEnteredIndex = 0;	//飞行物入场计数
	
	/*飞行物入场*/
	public void enterAction() {
		flyEnteredIndex++;
		if(flyEnteredIndex % 40 == 0) {	//400ms生成一个飞行物--10*40
			FlyingObject obj = nextOne();	//随机生成一个飞行物
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}
	
	int stepCount = 0;
	
	/*走一步*/
	public void stepAction() {
		stepCount++;
		for(int i = 0; i < flyings.length; i++) {	//飞行物走一步
			FlyingObject f = flyings[i];
			f.step();
		}
		
		for(int i = 0; i < bullets.length; i++) {	//子弹走一步
			Bullet b = bullets[i];
			b.step();
		}
		hero.step();	//英雄机走一步
		if(stepCount == 500) {
			hero.returnNormal();
			hero.setDoubleFire(0);	//双倍火力解除
		}
	}
	
	/*飞行物走一步*/
	public void flyingStepAction() {
		for(int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}
	
	int shootIndex = 0;	//射击计数
	
	/*射击*/
	public void shootAction() {
		shootIndex++;
		if(shootIndex % 30 == 0) {	//300ms发射一颗子弹
			Bullet[] bs = hero.shoot();	//英雄射击子弹
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);	//扩容
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);	//追加数组
		}
	}
	
	/*子弹与飞行物的碰撞检测*/
	public void bangAction() {
		for(int i = 0; i < bullets.length; i++) {	//遍历所有子弹
			Bullet b = bullets[i];
			bang(b);	//子弹和飞行物之间的碰撞检查
		}
	}
	
	/*删除越界的飞行物及子弹*/
	public void outOfBoundsAction() {
		int index = 0;	//索引
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];	//活着的飞行物
		for(int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if(!f.outOfBounds()) {
				flyingLives[index++] = f;	//不越界的飞行物不删除
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);	//将不越界的飞行物都留着
		
		index = 0;	//索引重置为0
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if(!b.outOfBounds()) {
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);	//将不越界的子弹都留着
	}
	
	/*检查游戏结束*/
	public void checkGameOverAction() {
		if(isGameOver() == true) {
			state = GAME_OVER;	//状态改变
		}
	}
	
	/*检查游戏是否结束*/
	public boolean isGameOver() {
		for(int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if(hero.hit(obj)) {	//检查英雄机与飞行物是否碰撞
				hero.lostLife();	//生命值减少
				hero.setDoubleFire(0);	//双倍火力解除
				hero.returnNormal();	//变身结束
				index = i;	//记录碰上的飞行物索引
			}
			if(index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;	//碰上的与最后一个交换位置
				flyings = Arrays.copyOf(flyings, flyings.length - 1);	//删除碰上的飞行物
			}
		}
		return hero.getLife() <= 0;
	}
	
	/*子弹和飞行物之间的碰撞检查*/
	public void bang(Bullet bullet) {
		int index = -1;	//击中的飞行物索引
		for(int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if(obj.shootBy(bullet)) {	//判断是否击中
				index = i;	//记录被击中飞行物的索引
				break;
			}
		}
		if(index != -1) {	//有击中的飞行物
			FlyingObject one = flyings[index];	//记录被击中的飞行物
			FlyingObject temp = flyings[index];	//被击中的飞行物与最后一个飞行物交换位置
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;
			
			flyings = Arrays.copyOf(flyings, flyings.length - 1);	//删除最后一个飞行物（被击中的）
			
			//检查one的类型（敌机加分，奖励获取）
			if(one instanceof Enemy) {	//检查类型，是敌机则加分
				Enemy e = (Enemy) one;	//强制类型转换
				score += e.getScore();	//加分
			}
			else {	//若为奖励，设置奖励
				Award a = (Award) one;
				int type = a.getType();	//获取奖励类型
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();//设置双倍火力
					stepCount = 0;
					break;
				case Award.LIFE:
					hero.addLife();	//设置增加生命值
					break;
				}
			}
		}
	}
	
	/*随机生成飞行物
	 * 返回的是飞行物对象
	 */
	public static FlyingObject nextOne() {
		Random random = new Random();
		int type = random.nextInt(20);	//[0,20)
		if(type < 4) {
			return new Star();
		}
		else {
			return new Airplane();
		}
	}
}
