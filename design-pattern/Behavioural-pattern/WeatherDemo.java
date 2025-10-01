import java.util.*;

interface Observer {
    void update(int temp);
}

class MobileApp implements Observer {
    public void update(int temp) { System.out.println("📱 App: Temp = " + temp); }
}

class DisplayBoard implements Observer {
    public void update(int temp) { System.out.println("🖥 Board: Temp = " + temp); }
}

class WeatherStation {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer o) { observers.add(o); }
    public void setTemperature(int temp) {
        System.out.println("WeatherStation: New Temp = " + temp);
        for (Observer o : observers) o.update(temp);
    }
}

public class WeatherDemo {
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation();
        ws.addObserver(new MobileApp());
        ws.addObserver(new DisplayBoard());
        ws.setTemperature(25);
    }
}
