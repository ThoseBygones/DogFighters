package dogfighters;

import java.util.Random;

public class Star extends FlyingObject implements Award {
	private int xSpeed = 1;	//x�����ƶ��ٶ�
	private int ySpeed = 2;	//y�����ƶ��ٶ�
	private int awardType;	//��������
	
	/*��ʼ������*/
	public Star() {
		this.image = ShootGame.star;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
		awardType = rand.nextInt(2);	//��ʼ��ʱ������
	}
	
	/*��ý�������*/
	public int getType() {
		return awardType;
	}
	
	/*Խ�紦��*/
	@Override
	public boolean outOfBounds() {
		return y > ShootGame.HEIGHT;
	}
	
	/*�ƶ�����б�ŷ�*/
	@Override
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if(x > ShootGame.WIDTH - width) {
			xSpeed = -1;
		}
		if(x < 0) {
			xSpeed = 1;
		}
	}
}