package dogfighters;

import java.awt.image.*;

/*飞行物（包括玩家飞机、敌机、子弹、收集物）*/
public abstract class FlyingObject {
	protected int x;	//x坐标
	protected int y;	//y坐标
	protected int width;	//宽度
	protected int height;	//高度
	protected BufferedImage image;	//图片
	
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
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	/*检查是否出界*/
	public abstract boolean outOfBounds();
	
	/*飞行物移动一步*/
	public abstract void step();
	
	/*检查当前飞行物体是否被子弹(x,y)击中
	 * @param Bullet 子弹对象
	 */
	public boolean shootBy(Bullet bullet) {
		int x = bullet.x;
		int y = bullet.y;
		return this.x < x && x < this.x + width && this.y < y && y < this.y + height;
	}
}
