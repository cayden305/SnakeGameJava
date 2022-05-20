import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class game {
    private Draw window;
    private Color background;
    private int width;
    private int height;
    private boolean gameStarted = false;
    private snake player;
    private food food;
    private int[][] grid;
    private int gridsize;
    private boolean gameover = false;
    private int gametick;


    public game(int width, int height, Color background, int gridsize) {
        window = new Draw("Snake Game");
        this.width = width;
        this.height = height;
        this.background = background;
        this.gridsize = gridsize;
        window.disableDoubleBuffering();
        window.setCanvasSize(width, height);
        window.clear(background);
        grid = new int[gridsize][gridsize];
    }

    public void controls() {
        if (window.isKeyPressed(39)) {
            if(player.getDirection() != snake.left){
                player.setDirection(snake.right);
            }
        } else if (window.isKeyPressed(37)) {
            if(player.getDirection() != snake.right){
                player.setDirection(snake.left);
            }
        } else if (window.isKeyPressed(38)) {
            if(player.getDirection() != snake.down){
                player.setDirection(snake.up);
            }
        } else if (window.isKeyPressed(40)) {
            if(player.getDirection() != snake.up){
                player.setDirection(snake.down);
            }
        }
    }

    public int[] getSnakeHead() {
        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                if (grid[i][j] == player.getLength()) {
                    return new int[]{i, j};
                }

            }
        }
        return (null);
    }

    public void spawnSnake() {
        int middle = gridsize / 2;
        grid[middle][middle] = 1;
    }

    public void colorStuff() {
        window.clear(Color.BLACK);
        window.enableDoubleBuffering();
        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                if (grid[i][j] > 0) {
                    window.setPenColor(Color.WHITE);
                    window.filledSquare((double) i / gridsize + 1. / gridsize / 2, (double) j / gridsize + 1. / gridsize / 2, 1. / gridsize / 2);
                }
                else if (grid[i][j] == -1){
                    window.setPenColor(Color.RED);
                    window.filledSquare((double) i / gridsize + 1. / gridsize / 2, (double) j / gridsize + 1. / gridsize / 2, 1. / gridsize / 2);
                }
            }
        }
        window.show();
    }

    public void subtractGrid() {
        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                if (grid[i][j] > 0) {
                grid[i][j]-=1;
                }
            }
        }
    }

    public void spawnFood() {
        int x = -1;
        int y = -1;
        while(x < 0 && y < 0) {
            x = (int) (Math.random() * gridsize);
            y = (int) (Math.random() * gridsize);
            if (grid[x][y] != 0){
                x = -1;
                y = -1;
            }
        }
        grid[x][y] = -1;
    }

    public void moveSnake(){
        int[] head = getSnakeHead();
        if(head == null){
            return;
        }

        int direction = player.getDirection();
        try{
        switch(direction) {
            case 1:
                if (grid[head[0] - 1][head[1]] == -1) {
                    grid[head[0] - 1][head[1]] = grid[head[0]][head[1]] + 1;
                    player.setLength(player.getLength() + 1);
                    spawnFood();
                } else if (grid[head[0] - 1][head[1]] > 0) {
                    gameover = true;
                } else {
                    grid[head[0] - 1][head[1]] = grid[head[0]][head[1]] + 1;
                    subtractGrid();
                }
                break;
            case 2:
                if (grid[head[0]][head[1] + 1] == -1) {
                    grid[head[0]][head[1] + 1] = grid[head[0]][head[1]] + 1;
                    player.setLength(player.getLength() + 1);
                    spawnFood();
                } else if (grid[head[0]][head[1] + 1] > 0) {
                    gameover = true;
                } else {
                    grid[head[0]][head[1] + 1] = grid[head[0]][head[1]] + 1;
                    subtractGrid();
                }
                break;
            case 3:
                if (grid[head[0] + 1][head[1]] == -1) {
                    grid[head[0] + 1][head[1]] = grid[head[0]][head[1]] + 1;
                    player.setLength(player.getLength() + 1);
                    spawnFood();
                } else if (grid[head[0] + 1][head[1]] > 0) {
                    gameover = true;
                } else {
                    grid[head[0] + 1][head[1]] = grid[head[0]][head[1]] + 1;
                    subtractGrid();
                }
                break;
            case 4:
                if (grid[head[0]][head[1] - 1] == -1) {
                    grid[head[0]][head[1] - 1] = grid[head[0]][head[1]] + 1;
                    player.setLength(player.getLength() + 1);
                    spawnFood();
                } else if (grid[head[0]][head[1] - 1] > 0) {
                    gameover = true;
                } else {
                    grid[head[0]][head[1] - 1] = grid[head[0]][head[1]] + 1;
                    subtractGrid();
                }
                break;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            gameover = true;
        }
    }
    public void mainGame(){
        player = new snake();
        spawnSnake();
        spawnFood();
        colorStuff();
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if (!gameover) {
                    controls();
                    moveSnake();
                    colorStuff();
                    // \/ alternate movement system (buggy)
                    //gametick++;
                    //if (gametick % 10 == 0){
                    //    moveSnake();
                    //    colorStuff();
                   // }
                }
            }
        },0,100);
    }
    public boolean isGameover(){
        return gameover;
    }
}
