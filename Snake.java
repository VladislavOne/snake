package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;

    private Direction direction = Direction.LEFT;

    public void setDirection(Direction direction) {
        if (direction == Direction.DOWN && this.direction == Direction.UP){
            return;
        } else if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        }else if (direction == Direction.LEFT && this.direction == Direction.RIGHT){
            return;
        }

        this.direction = direction;
    }

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    private final List<GameObject> snakeParts = new ArrayList<>();

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            if (!isAlive) {
                game.setCellValueEx(part.x, part.y, Color.NONE, sign, Color.RED, 75);
            } else {
                game.setCellValueEx(part.x, part.y, Color.NONE, sign, Color.BLACK, 75);
            }
        }
    }

    public void move(Apple apple) {
        GameObject createNewHead = createNewHead();
        if (createNewHead.x < 0 || createNewHead.x >= SnakeGame.WIDTH || createNewHead.y < 0 || createNewHead.y >= SnakeGame.HEIGHT) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, createNewHead);

        if (createNewHead.x == apple.x && createNewHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject head = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(head.x - 1, head.y);

        } else if (direction == Direction.RIGHT) {
            return new GameObject(head.x + 1, head.y);
        } else if (direction == Direction.DOWN) {
            return new GameObject(head.x, head.y + 1);
        } else if (direction == Direction.UP) {
            return new GameObject(head.x, head.y - 1);
        } else {
            return new GameObject(head.x, head.y);
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }
}
