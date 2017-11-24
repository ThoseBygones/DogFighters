package dogfighters;

import java.awt.image.*;

public class Hero extends FlyingObject {
	private BufferedImage[] images = {};	//英雄机图片
	//private int index = 0;	//英雄机图片切换索引
	
	private int doubleFire;	//双倍火力
	private int life;	//生命数
	
	/*初始化数据*/
	public Hero() {
		life = 3;	//初始拥有3条命
		doubleFire = 0;	//初始没有双倍火力
		images = new BufferedImage[] {ShootGame.hero0, ShootGame.hero1};	//英雄机图片
		image = ShootGame.hero0;	//初始化是0号机
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
	}
	
	/*获得双倍火力*/
	public int isDoubleFire() {
		return doubleFire;
	}
	
	/*设置双倍火力*/
	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}
	
	/*增加火力*/
	public void addDoubleFire() {
		doubleFire = 40;
		image = images[1];	//切换图片
	}
	
	/*生命值增加*/
	public void addLife() {
		life++;
	}

	/*生命值减少*/
	public void lostLife() {
		life--;
	}
	
	/*获得当前生命值*/
	public int getLife() {
		return life;
	}
	
	/*当前物体移动了一下*/
	public void moveTo(int x,int y) {
		this.x = x - width / 2;
		this.y = y - height / 2;
	}
	
	/*越界处理*/
	@Override
	public boolean outOfBounds() {
		return false;
	}
	
	/*发射子弹*/
	public Bullet[] shoot() {
		int xStep = width / 4;
		int yStep = 20;	//步
		if(doubleFire>0) {	//当前是双倍火力
			Bullet [] bullets = new Bullet[2];
			bullets[0] = new Bullet(x+xStep,y-yStep);	//y-yStep（子弹距飞机的距离）
			bullets[1] = new Bullet(x+3*xStep,y-yStep);
			return bullets;
		}
		else {
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(x+2*xStep,y-yStep);
			return bullets;
		}
	}
	
	/*移动*/
	@Override
	public void step() {
		/*
		if(images.length>0) {
			image = images[index++/10%images.length];	//切换图片hero0,hero1
		}
		*/
	}
	
	/*改变图片*/
	public void returnNormal() {
		image = images[0];
	}
	
	/*碰撞*/
	public boolean hit(FlyingObject other) {
		int x1 = other.x - this.width / 2;	//x坐标的最小距离
		int x2 = other.x + this.width / 2 + other.width;	//x坐标的最大距离
		int y1 = other.y - this.height / 2;	//y坐标的最小距离
		int y2 = other.y - this.height / 2 + other.height;	//y坐标的最大距离
		
		int herox = this.x + this.width / 2;	//英雄机x坐标中心点坐标
		int heroy = this.y + this.height / 2;	//英雄机y坐标中心点坐标
		
		return herox > x1 && herox < x2 && heroy > y1 && heroy < y2;	//在该区间范围内则认为装上了
	}
}
