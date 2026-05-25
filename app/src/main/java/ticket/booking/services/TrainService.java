package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entity.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    private List<Train> trainList;
    private ObjectMapper mapper = new ObjectMapper();
    private static final String TRAIN_PATH = "app/src/main/java/ticket/booking/localDB/trains.json";

    public TrainService() throws IOException {
        trainList = loadTrains();
    }

    public List<Train> loadTrains() throws IOException {
        File file = new File(TRAIN_PATH);
        if (!file.exists()) {
            mapper.writeValue(file, List.of());
        }
        return mapper.readValue(file, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream()
                .filter(train -> validTrain(train, source, destination))
                .collect(Collectors.toList());
    }

    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        int sourceIndex = -1;
        int destinationIndex = -1;

        for (int i = 0; i < stationOrder.size(); i++) {
            if (stationOrder.get(i).equalsIgnoreCase(source)) sourceIndex = i;
            if (stationOrder.get(i).equalsIgnoreCase(destination)) destinationIndex = i;
        }

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    public boolean bookSeat(Train train) throws IOException {
        for (Train t : trainList) {
            if (t.getTrainId().equals(train.getTrainId())) {
                // Find first available seat
                for (List<Integer> row : t.getSeats()) {
                    for (int i = 0; i < row.size(); i++) {
                        if (row.get(i) == 0) {
                            row.set(i, 1); // mark as booked
                            saveTrains();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void saveTrains() throws IOException {
        mapper.writeValue(new File(TRAIN_PATH), trainList);
    }
}
