import java.util.Objects;

class Student implements Cloneable{
    public String name = "Vlad";
    public int age = 19;
    public Student(){}
    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString(){
        return "Student: Name - " + name + " Age - " + age;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj){ // проверка ссылки на себя же
            return true;
        }
        if(!(obj instanceof Student)){ //Можно использовать instanсeof(разрешает подклассы)
            return false;
        }
        Student student = (Student) obj; // Приведения типов, потому что equals должен принимать любой тип
        return age == student.age && Objects.equals(name, student.name);
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}

public class Test {

    public static void main(String[] args) {
        Auto auto = new Auto("Audi", 10);
        System.out.println(auto);

        StringBuffer buf = new StringBuffer();
        buf.append("Жопа");
        buf.delete(1,3);
        buf.insert(2, "Hello");
        System.out.println(buf);

        Student student1 = new Student();
        Student student2 = new Student("Artem", 123);
        System.out.println(student1.toString());
        System.out.println(student2.toString());
        System.out.println(student1.toString());

        System.out.println(student1.equals(student2));
    }
}
