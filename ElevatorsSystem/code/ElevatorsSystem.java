package sample;

import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ElevatorsSystem implements Runnable {
    private final int elevators_num, floors_num, people_num;
    private final int requests_num;
    private final Elevator[] elevators;
    private Stage stage;

    static class Request {
        public int floor, people_num;
        public boolean direction;

        public Request(int floor, int people_num, boolean direction) {
            this.floor = floor;
            this.people_num = people_num;
            this.direction = direction;
        }
    }

    public ElevatorsSystem(int elevators_num, int floors_num, int people_num, int requests_num, Stage stage) {
        this.elevators_num = elevators_num;
        this.floors_num = floors_num;
        this.people_num = people_num;
        this.requests_num = requests_num;
        this.stage = stage;
        elevators = new Elevator[elevators_num];
        for (int i = 0; i < elevators_num; i++) {
            elevators[i] = new Elevator(people_num, floors_num, i, stage);
        }
    }

    private Request generate_random_request(int max_floor, int max_people_num) {
        Random ran = new Random();
        int floor = 1 + ran.nextInt(max_floor - 1);
        int people_num = 1 + ran.nextInt(max_people_num);
        boolean direction = ran.nextBoolean();
        return new Request(floor, people_num, direction);
    }

    private Elevator find_best_elevator(Request request) {
        Elevator best_elevator = null;
        int best_floor_dif = floors_num + 1;
        for (Elevator elevator: elevators) {
            int floor_dif = Math.abs(request.floor - elevator.getCur_floor());
            if (floor_dif >= best_floor_dif) {
                continue;
            }
            if (!elevator.isBusy())
                    // || elevator.isBusy() && request.direction == elevator.getDirection()
                    // && elevator.getCapacity() + request.people_num <= elevator.getMax_capacity())
            {
                best_floor_dif = floor_dif;
                best_elevator = elevator;
            }
        }
        return best_elevator;
    }

    private LinkedList<Request> generate_requests_flow(int req_num) {
        LinkedList<Request> requests = new LinkedList<>();
        for (int i = 0; i < req_num; i++) {
            Request req = generate_random_request(floors_num, people_num);
            requests.add(req);
        }
        return requests;
    }

    public void launch_elevators() {
        LinkedList<Request> requests = generate_requests_flow(requests_num);
        Executor executor = Executors.newFixedThreadPool(elevators_num);

        while (requests.size() > 0) {
            Request req = requests.getFirst();
            Elevator elevator = find_best_elevator(req);

            if (elevator != null) {
                elevator.setReq(req);
                requests.removeFirst();

                // System.out.println("NEW " + elevator);
                executor.execute(elevator);
                // System.out.println("Number of tasks: " + requests.size());
            }
        }
    }

    @Override
    public void run() {
        launch_elevators();
    }
}
