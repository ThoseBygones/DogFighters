package dogfighters;

import java.util.Random;

//敌机：是飞行物，是敌人
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;	//初始化移动速度为3
	
	/*初始化数据*/
	public Airplane() {
		this.image = ShootGame.enemyplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
	}
	
	/*获取分数*/
	@Override
	public int getScore() {
		return 5;
	}
	
	/*越界处理*/
	@Override
	public boolean outOfBounds() {
		return y > ShootGame.HEIGHT;
	}
	
	/*移动*/
	@Override
	public void step() {
		y += speed;
	}
}
