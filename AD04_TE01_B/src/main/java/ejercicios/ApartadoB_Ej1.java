package ejercicios;

import java.time.LocalDate;

import entidades.Address;
import entidades.Student;
import entidades.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class ApartadoB_Ej1 {

	/**
	 * 1. OneToMany bidireccional entre entidades Student y University
	 */
	public static void main(String[] args) {

		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ud4");
        EntityManager entityManager = factory.createEntityManager();
        
        
		
		
		try {			
			// crea un objeto Student y University
			System.out.println("Creando un nuevo objeto Student y una Universidad");
		
			Student student = createStudent();
			University university = createUniversity();
					
			university.getStudents().add(student);
			student.setUniversity(university); //bidireccional
					
			// comienza la transacci n
			entityManager.getTransaction().begin();
			//session.beginTransaction();
			
			// guarda el objeto University
			System.out.println("Guardando la universidad y en cascada el estudiante");
			entityManager.persist(university);
			
			// hace commit de la transaccion
			entityManager.getTransaction().commit();
			
			// prueba para acceder a la entidad source desde de la entidad target
			//entityManager.beginTransaction();
			Student dbStudent = (Student)entityManager.find(Student.class, university.getStudents().get(0).getId());
			System.out.println(dbStudent.getUniversity().getName());
						
			System.out.println("Hecho!");
		}
		catch ( Exception e ) {
			// rollback ante alguna excepci n
			System.out.println("Realizando Rollback");
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			entityManager.close();
			factory.close();
		}
	}
	private static Student createStudent() {
		Student tempStudent = new Student();
		Address tempAddress = new Address();
		
		tempStudent.setFirstName("Gaizka");
		tempStudent.setLastName("Toquero");
		tempStudent.setEmail("ilaspiur@birt.eus");
		tempStudent.getPhones().add("687123456");
		tempStudent.getPhones().add("699212345");
		tempStudent.setBirthdate(LocalDate.parse("1985-04-04"));
		tempAddress.setAddressLine1("Burdin kale 8");
		tempAddress.setAddressLine2("1A");
		tempAddress.setCity("Zarautz");
		tempAddress.setZipCode("20080");
		tempStudent.setAddress(tempAddress);
		return tempStudent;		
	}
	
	private static University createUniversity() {
		University tempUniversity = new University();
		Address uniAddress = new Address();
		
		tempUniversity.setName("BIRT");
		uniAddress.setAddressLine1("Araba Kalea");
		uniAddress.setAddressLine2("5");
		uniAddress.setCity("Gasteiz");
		uniAddress.setZipCode("01155");
		tempUniversity.setAddress(uniAddress);
		return tempUniversity;		
	}
}










