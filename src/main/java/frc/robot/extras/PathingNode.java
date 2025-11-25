package frc.robot.extras;

public class PathingNode {
    double x;
    double y;
    double speed;

    public PathingNode(double x, double y, double speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    
    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getSpeed(){
        return speed;
    }
}