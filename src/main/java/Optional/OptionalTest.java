package Optional;

import java.util.Optional;

public class OptionalTest {


    /**
     * 深层质疑
     */
//    public String getCarInsuranceName(Person person) {
//        if (person != null) {
//            Car car = person.getCar();
//            if (car != null) {
//                Insurance insurance = car.getInsurance();
//                if (insurance != null) {
//                    return insurance.getName();
//                }
//            }
//        }
//        return "Unknown";
//    }


    /**
     * 多路退出
     */
//    public String getCarInsuranceName2(Person person) {
//        if (person == null) {
//            return "Unknown";
//        }
//        Car car = person.getCar();
//        if (car == null) {
//            return "Unknown";
//        }
//        Insurance insurance = car.getInsurance();
//        if (insurance == null) {
//            return "Unknown";
//        }
//        return insurance.getName();
//    }
    public static void main(String[] args) {
        Person person1 = new Person();
        Insurance insurance = new Insurance();
        insurance.setName("aassdd");
        Car car = new Car();
        car.setInsurance(insurance);
        person1.setCar(car);
        person1.setAge(20);
        Optional<Person> person = Optional.of(person1);
        String carInsuranceName = getCarInsuranceName(person, 10);
        System.out.println("carInsuranceName = " + carInsuranceName);



    }


    public static String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }


}
