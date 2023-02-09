package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.enties.Department;
import model.enties.Seller;

public class Program2 {

	public static void main(String args[]) {

		SellerDao sd = DaoFactory.createSellerDao();
		Scanner read= new Scanner(System.in);

		System.out.println("=== Teste Número 1 : seller find By Id ===");
		Seller seller = sd.findById(2);
		System.out.println(seller);

		System.out.println();
		System.out.println("=== Teste Número 2 : seller find ByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list = sd.findByDepartment(department);
		for (Seller s : list) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("=== Teste Número 3 : seller find ByAll ===");
		list = sd.findAll();
		for (Seller s : list) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("=== Teste Número 4 : seller insert ===");
		Seller seller1 = new Seller(null,"Greg","greg@gmail.com",new Date(),4000.0,department);
		sd.insert(seller1);
		System.out.println("Inserted! new id = " + seller1.getId());
		
		System.out.println();
		System.out.println("=== Teste Número 5 : seller upDate ===");
		seller = sd.findById(1);
		seller.setName("Marth Wayne");
		sd.update(seller);;
		System.out.println("Update completed!");
		

		System.out.println();
		System.out.println("=== Teste Número 6 : seller delete ===");
		System.out.println("enter id for delete test!");
		int x = read.nextInt();
		sd.deleteById(x);
		System.out.println("Delete completed");
		read.close();

	}

}
