package net.pulga22.particlestudio.core.editor.handlers;

import net.pulga22.particlestudio.core.routines.ParticlePoint;

import java.util.ArrayList;
import java.util.List;

public class SelectionHandler{

    private final List<ParticlePoint> selectedPoints = new ArrayList<>();

    public SelectionHandler(){

    }

    public void add(ParticlePoint point){
        this.selectedPoints.add(point);
    }

    public List<ParticlePoint> get(){
        return selectedPoints;
    }

    public void clear(){
        selectedPoints.clear();
    }

}
