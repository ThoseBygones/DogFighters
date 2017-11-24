package dogfighters;

import java.util.Random;

//�л����Ƿ�����ǵ���
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;	//��ʼ���ƶ��ٶ�Ϊ3
	
	/*��ʼ������*/
	public Airplane() {
		this.image = ShootGame.enemyplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
	}
	
	/*��ȡ����*/
	@Override
	public int getScore() {
		return 5;
	}
	
	/*Խ�紦��*/
	@Override
	public boolean outOfBounds() {
		return y > ShootGame.HEIGHT;
	}
	
	/*�ƶ�*/
	@Override
	public void step() {
		y += speed;
	}
}
