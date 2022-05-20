import java.awt.*;

public class snake {
    private int direction;
    public static int right = 3;
    public static int left = 1;
    public static int up = 2;
    public static int down = 4;
    private int length;
    private static Color snakeColor = Color.white;
    public snake(){
        length = 1;
        direction = right;
    }
    public int getLength(){
        return length;
    }
    public void setLength(int length){
        this.length=length;
    }


    public void setDirection(int direction){
        this.direction = direction;
    }
    public int getDirection(){
        return direction;
    }
}
