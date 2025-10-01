interface Shape { void draw(); }

class Circle implements Shape {
    public void draw() { System.out.println("Drawing Circle"); }
}

class Rectangle implements Shape {
    public void draw() { System.out.println("Drawing Rectangle"); }
}

class ShapeFactory {
    public static Shape createShape(String type) {
        if (type.equalsIgnoreCase("circle")) return new Circle();
        if (type.equalsIgnoreCase("rectangle")) return new Rectangle();
        return null;
    }
}

public class ShapeDemo {
    public static void main(String[] args) {
        Shape s1 = ShapeFactory.createShape("circle");
        s1.draw();
        Shape s2 = ShapeFactory.createShape("rectangle");
        s2.draw();
    }
}
