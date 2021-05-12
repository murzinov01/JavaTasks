package sample;

import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;


public class Elevator implements Runnable {
    private boolean busy;
    private boolean direction;
    private int cur_floor, target_floor, max_floor, capacity, max_capacity, id;
    private final long move_time;
    private Rectangle elevator;
    private Stage stage;

    public Elevator() {
        busy = direction = false;
        cur_floor = target_floor = max_capacity = capacity = max_floor = id = 0;
        move_time = 3000;
    }

    public Elevator(int max_capacity, int max_floor, int id, Stage stage) {
        this();
        this.max_floor = max_floor;
        this.max_capacity = max_capacity;
        this.id = id;
        this.stage = stage;
        elevator = new Rectangle(50, 50 , 200, 100);
    }

    public synchronized int getCur_floor() {
        return cur_floor;
    }

    public synchronized boolean isBusy() {
        return busy;
    }

    public boolean getDirection() {
        return direction;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMaxCapacity(int capacity) {
        this.max_capacity = capacity;
    }

    public void setMax_floor(int max_floor) {
        this.max_floor = max_floor;
    }

    public void setReq(ElevatorsSystem.Request request) {
        cur_floor = request.floor;
        busy = true;
        capacity += request.people_num;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", " + cur_floor + " -> " + target_floor +
                '}';
    }

    private int generate_next_floor() {
        Random rand = new Random();
        LinkedList<Integer> floors = new LinkedList<>();
        for (int i = 0; i < max_floor; i++) {
            floors.add(i);
        }
        floors.remove(cur_floor);
        return floors.get(rand.nextInt(max_floor - 1));
    }

    private void move() throws InterruptedException {
        while (cur_floor != target_floor) {
            System.out.println(this);
            Thread.sleep(move_time);
            if (cur_floor < target_floor) {
                cur_floor++;
            } else {
                cur_floor--;
            }
        }
        busy = false;
        capacity = 0;
    }

    @Override
    public void run() {
        // System.out.println("Elevator is starting moving");
        // show(stage);
        try {
            target_floor = generate_next_floor();
            direction = target_floor > cur_floor;
            move();
        }
        catch (InterruptedException e) {
            System.out.println("ERROR occurred while moving from " + cur_floor + " to " + (cur_floor + 1));
            System.exit(1);
        }
    }

    private void show(Stage stage) {
        Scene scene = stage.getScene();
        // System.out.println(scene);
        Group group = new Group(elevator);
        // Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }
}
