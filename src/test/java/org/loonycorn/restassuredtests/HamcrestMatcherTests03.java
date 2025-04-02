package org.loonycorn.restassuredtests;

import org.loonycorn.Student;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatcherTests03 {

    @Test
    public void testStudentObject() {
        // Instancie l'objet Student
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        // Utilise le comparateur equalTo() pour voir si l'objet est égal à celui instancié
        assertThat(student, equalTo(new Student("Jessica Lang", "Computer Science", 3.8)));
    }

    @Test
    public void testStudentObjectInstanceOf() {
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        // Vérifier si l'objet student est une instance du Student.class
        assertThat(student, instanceOf(Student.class));
    }

    @Test
    public void testStudentObjectNullValue() {
        Student nullStudent = null;
        // le comparateur nullValue pour vérifier qu'une valeur est null
        assertThat(nullStudent, nullValue());
    }

    @Test
    public void testStudentObjectNotNullValue() {
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        // Vérifier que l'objet student ne soit pas null
        assertThat(student, notNullValue());
    }

    @Test
    public void testStudentObjectSameInstance () {
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        // configure la variable sameStudent pour qu'elle pointe vers l'objet student d'origine
        Student sameStudent = student;
        // vérifier si les 2 objets sont exactement la même instance, s'ils pointent vers le même objet en mémoire
        assertThat(student,sameInstance(sameStudent));
    }

    @Test
    public void testStudentObjectHasToString () {
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        // Vérifier que l'objet est une chaîne et suit un certain format
        assertThat(student,hasToString("Student{name=Jessica Lang, major=Computer Science, gpa=3.8}"));
    }

    @Test
    public void testStudentObjectHasProprety() {
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        // vérifier si l'objet contient certaines propriétés
        assertThat(student,hasProperty("name", equalTo("Jessica Lang")));
        assertThat(student,hasProperty("major", equalTo("Computer Science")));
        assertThat(student,hasProperty("gpa", equalTo(3.8)));
    }

    @Test
    public void testStudentObjectsHaveSameProperty () {
        Student student = new Student("Jessica Lang", "Computer Science", 3.8);
        Student anotherStudent = new Student("Jessica Lang", "Computer Science", 3.8);
        // vérifier si les 2 objets possèdent les mêmes propriétés
        assertThat(student,samePropertyValuesAs(anotherStudent));
    }

}
