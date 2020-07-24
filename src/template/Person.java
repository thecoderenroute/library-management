package template;

import java.util.Objects;

public class Person {

    private String fName;
    private String lName;
    private String midName;
    private String name;

    public Person(String name) {
        this.properName(name);
    }

    private void properName(String name) {

        String[] fullName;
        if (name.contains(".")) {
            fullName = name.trim().split("\\.");
        } else {
            fullName = name.trim().split(" ");
        }
        for (int i = 0; i < fullName.length; i++) {
            fullName[i] = fullName[i].trim();
        }

        if (fullName.length == 1) {
            lName = fullName[0];
            this.name = name;
        } else if (fullName.length == 2) {
            fName = fullName[0];
            lName = fullName[1];
            this.name = fName + ". " + lName;
        } else if (fullName.length == 3) {
            fName = fullName[0];
            midName = fullName[1];
            lName = fullName[2];
            this.name = fName + ". " + this.midName + ". " + this.lName;

        } else if (fullName.length == 0) {
            throw new IllegalArgumentException();
        } else {
            fName = fullName[0];
            lName = fullName[fullName.length - 1];
            StringBuilder str = new StringBuilder();
            for (int i = 1; i < fullName.length - 1; i++) {
                str.append(fullName[i]);
                str.append(". ");
            }
            str.replace(str.length() - 2, str.length(), "");
            midName = str.toString();
            this.name = fName + ". " + midName + ". " + lName;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!Objects.equals(fName, person.fName)) return false;
        if (!lName.equals(person.lName)) return false;
        return Objects.equals(midName, person.midName);
    }

    public String getfName() {
        return fName == null ? "" : this.fName;
    }

    public String getlName() {
        return lName;
    }

    public String getMidName() {
        return midName == null ? "" : this.midName;
    }

    @Override
    public int hashCode() {
        int result = fName != null ? fName.hashCode() : 0;
        result = 31 * result + lName.hashCode();
        result = 31 * result + (midName != null ? midName.hashCode() : 0);
        return result;
    }

    public boolean contains(String searched) {
        String[] words = searched.split(" ");
        for (String word : words) {
            if (this.getfName().toLowerCase().contains(word.toLowerCase()) ||
                    this.getlName().toLowerCase().contains(word.toLowerCase()) ||
                    this.getMidName().toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
