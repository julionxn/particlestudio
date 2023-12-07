package net.pulga22.particlestudio.core.routines;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Timeline implements Serializable {

    private final List<List<ParticlePoint>> timeline = new ArrayList<>();
    private int displayLength;
    private int actualLength;
    private transient int currentEditingTick;
    private transient int onionLowerBound;
    private transient int onionUpperBound;

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        while (timeline.size() <= time) {
            timeline.add(new ArrayList<>());
        }
        List<ParticlePoint> particleList = timeline.get(time);
        particleList.add(particlePoint);
        if (time > actualLength) {
            actualLength = time;
            displayLength = time;
            adjustOnionUpperBound(actualLength - onionUpperBound());
        }
        if (time == displayLength){
            adjustFrame(1);
            adjustOnionUpperBound(1);
        }
    }

    public void adjustFrame(int in){
        if (in == 0) return;
        if (in < 0) { decreaseFrame(in); return; }
        increaseFrame(in);
    }

    private void decreaseFrame(int in){
        if (currentEditingTick - in < 0 || displayLength - in < actualLength) return;
        displayLength -= in;
        currentEditingTick -= in;
    }

    private void increaseFrame(int in){
        displayLength += in;
        currentEditingTick += in;
    }

    public void adjustOnionUpperBound(int in){
        if (in == 0) return;
        int newBound = onionUpperBound + in;
        if (newBound > actualLength || newBound <= onionLowerBound) return;
        onionUpperBound += in;
    }

    public void adjustOnionLowerBound(int in){
        if (in == 0) return;
        int newBound = onionLowerBound + in;
        if (newBound < 0 || newBound >= onionUpperBound) return;
        onionLowerBound += in;
    }

    public int getCurrentEditingTick(){
        return currentEditingTick;
    }

    public int displayLength(){
        return displayLength;
    }

    public int length() {
        return actualLength;
    }

    public int onionLowerBound(){
        return onionLowerBound;
    }

    public int onionUpperBound(){
        return onionUpperBound;
    }

    public boolean isEmpty(){
        return timeline.isEmpty();
    }

    public List<ParticlePoint> getPointsOfTick(int tick){
        return timeline.get(tick);
    }

    public List<List<ParticlePoint>> getPoints(){
        return timeline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeline timeline1 = (Timeline) o;
        return displayLength == timeline1.displayLength && actualLength == timeline1.actualLength && Objects.equals(timeline, timeline1.timeline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeline, displayLength, actualLength);
    }
}
