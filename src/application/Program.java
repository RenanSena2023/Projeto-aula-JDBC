package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.enties.Department;
import model.enties.Seller;

public class Program {

	public static void main(String args[]) {

		DepartmentDao sd = DaoFactory.createDepartmentDao();
		Scanner read = new Scanner(System.in);

		System.out.println("=== Teste Número 1 : seller find By Id ===");
		Department dep = sd.findById(3);
		System.out.println(dep);

/*
		System.out.println();
		System.out.println("=== Teste Número 2 : Department insert ===");
		dep= new Department(null,"Sportes");
		sd.insert(dep);
		System.out.println("Inserted! new id = " + dep.getId());
		*/
		System.out.println();
		System.out.println("=== Teste Número 3 : Department upDate ===");
		Department dep2 = sd.findById(1);
		dep2.setName("Computers");
		sd.update(dep2);;
		System.out.println("Update completed!");
		

		System.out.println();
		System.out.println("=== Teste Número 4 : Department delete ===");
		System.out.println("enter id for delete test!");
		int x = read.nextInt();
		sd.deleteById(x);
		System.out.println("Delete completed");
		read.close();
		
		System.out.println();
		System.out.println("=== Teste Número 5 : Department findAll ===");
		List<Department> list = new ArrayList<>();
		list = sd.findAll();
		
		list.forEach(System.out::println);
		

	}

}
