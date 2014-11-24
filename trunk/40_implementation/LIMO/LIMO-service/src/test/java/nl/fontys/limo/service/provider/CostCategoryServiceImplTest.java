//package nl.fontys.limo.service.provider;
//
//import java.util.List;
//import nl.fontys.sofa.limo.api.service.provider.CostCategoryService;
//import nl.fontys.sofa.limo.domain.category.CostCategory;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.openide.util.Lookup;
//import org.openide.util.Lookup.Result;
//
///**
// *
// * @author Sebastiaan Heijmann
// */
//public class CostCategoryServiceImplTest {
//	private CostCategoryService service;
//	private Result result;
//	
//	public CostCategoryServiceImplTest() {
//	}
//	
//	@BeforeClass
//	public static void setUpClass() {
//	}
//	
//	@AfterClass
//	public static void tearDownClass() {
//	}
//	
//	@Before
//	public void setUp() {
//		service = new CostCategoryServiceImpl();
//	}
//	
//	@After
//	public void tearDown() {
//		service = null;
//	}
//
//	/**
//	 * Test of find method, of class CostCategoryServiceImpl.
//	 */
//	@Test
//	public void testFind() {
//		System.out.println("find");
//		int id = 0;
//		CostCategoryServiceImpl instance = new CostCategoryServiceImpl();
//		CostCategory expResult = null;
//		CostCategory result = instance.find(id);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of findAll method, of class CostCategoryServiceImpl.
//	 */
//	@Test
//	public void testFindAll() {
//		System.out.println("findAll");
//		CostCategoryServiceImpl instance = new CostCategoryServiceImpl();
//		List<CostCategory> expResult = null;
//		List<CostCategory> result = instance.findAll();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of insert method, of class CostCategoryServiceImpl.
//	 */
//	@Test
//	public void testInsert() {
//		System.out.println("insert");
//		CostCategory t = null;
//		CostCategoryServiceImpl instance = new CostCategoryServiceImpl();
//		CostCategory expResult = null;
//		CostCategory result = instance.insert(t);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of update method, of class CostCategoryServiceImpl.
//	 */
//	@Test
//	public void testUpdate() {
//		System.out.println("update");
//		CostCategory t = null;
//		CostCategoryServiceImpl instance = new CostCategoryServiceImpl();
//		boolean expResult = false;
//		boolean result = instance.update(t);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of delete method, of class CostCategoryServiceImpl.
//	 */
//	@Test
//	public void testDelete() {
//		System.out.println("delete");
//		CostCategory t = null;
//		CostCategoryServiceImpl instance = new CostCategoryServiceImpl();
//		boolean expResult = false;
//		boolean result = instance.delete(t);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getLookup method, of class CostCategoryServiceImpl.
//	 */
//	@Test
//	public void testGetLookup() {
//		// Testing empty lookup
//		result = service.getLookup().lookupResult(CostCategory.class);
//		assertEquals("No item in lookup", result.allInstances().size(), 0);
//	}
//	
//}
