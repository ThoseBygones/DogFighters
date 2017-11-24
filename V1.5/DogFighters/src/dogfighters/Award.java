package dogfighters;

/*奖励*/
public interface Award {
	int DOUBLE_FIRE = 0;	//双倍火力
	int LIFE = 1;	//1条命
	/*获得奖励类型：0表示获得双倍火力，1表示获得1条命*/
	int getType();
}
