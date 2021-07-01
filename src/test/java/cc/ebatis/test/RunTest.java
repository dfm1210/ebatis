package cc.ebatis.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import cc.ebatis.create.CreateExcel;
import cc.ebatis.impl.Init;
import cc.ebatis.pojo.ActionContext;
import cc.ebatis.test.pojo.CreateExcelPOJO;
import cc.ebatis.test.pojo.ImportPojo;
import cc.ebatis.test.pojo.RealPojo;

public class RunTest {
	
	// 计时
	public long a1;
	public long a2;
	
	/*
	 * 完全正确的数据（格式完全正确/流操作）
	 */
	@Test
	public void importTestAllRightInputStream() throws Exception {
		// =========== xlsx for sax
		runInitForStream("exl/allright.xlsx", false);
		// =========== xls for usermodel
		runInitForStream("exl/allright.xls", false);
	}
	
	/*
	 * 完全正确的数据（格式完全正确）
	 */
	@Test
	public void importTestAllRight() throws Exception {
		// =========== xlsx for sax
		runInit("exl/allright.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/allright.xls", false);
	}
	
	/*
	 * 带函数公式的（格式完全正确）
	 */
	@Test
	public void importTestAllRightFunction() throws Exception {
		// =========== xlsx for sax
		runInit("exl/function.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/function.xls", false);
	}
	
	/*
	 * 日期包含多种格式的（但单元格都是日期类型）
	 */
	@Test
	public void manyDateTest() throws Exception {
		// =========== xlsx for sax
		runInit("exl/manydate.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/manydate.xls", false);
	}
	
	/*
	 * 仅包含空cell，空的将赋值为null
	 */
	@Test
	public void blankCellTest() throws Exception {
		// =========== xlsx for sax
		runInit("exl/blankcell.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/blankcell.xls", false);
	}
	
	/*
	 * 空表头
	 */
	@Test
	public void blankCellHeadTest() throws Exception {
		// =========== xlsx for sax
		runInit("exl/blankcellhead.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/blankcellhead.xls", false);
	}
	
	
	/*
	 * 空行测试
	 */
	@Test
	public void blankRowTest() throws Exception {
		// =========== xlsx for sax
		runInit("exl/blankrow.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/blankrow.xls", false);
	}
	
	/*
	 * 多sheet测试（格式完全正确）
	 */
	@Test
	public void manyRightSheetTest() throws Exception {
		// =========== xlsx for sax
		runInit("exl/manyrightsheet.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/manyrightsheet.xls", false);
	}
	
	/*
	 * 空的sheet包含（只包含表头或什么都没有）
	 */
	@Test
	public void manyBlankSheetTest() throws Exception {
		// =========== xlsx for sax
		runInit("exl/manyblanksheet.xlsx", false);
		// =========== xls for usermodel
		runInit("exl/manyblanksheet.xls", false);
	}

	//@Test
	public void realTest() {
		ActionContext<RealPojo> act = null;
		Init<RealPojo> init = null;
		a1 = System.currentTimeMillis();
		File file = new File("exl/性能测试20.xlsx");
		init = new Init<RealPojo>(file, RealPojo.class, false);
		act = init.start();
		System.out.println(act.getSheets().get(0).getInfo().get(0));
		System.out.println("over my head");
	}
	
	
	/*
	 * run 程序
	 */
	public void runInit(String fileName, boolean distinct) throws Exception {
		ActionContext<ImportPojo> act = null;
		Init<ImportPojo> init = null;
		a1 = System.currentTimeMillis();
		File file = new File(fileName);
		init = new Init<ImportPojo>(file, ImportPojo.class, distinct);
		act = init.start();
		printInfo(act);
	}
	
	/*
	 * run 程序 流操作
	 */
	public void runInitForStream(String fileName, boolean distinct) throws Exception {
		ActionContext<ImportPojo> act = null;
		Init<ImportPojo> init = null;
		a1 = System.currentTimeMillis();
		File file = new File(fileName);
		InputStream in = new FileInputStream(file);
		init = new Init<ImportPojo>(in, ImportPojo.class, distinct);
		act = init.start();
		printInfo(act);
	}
	
	/**
	 * 打印信息结果
	 * @param act
	 */
	public void printInfo(ActionContext<ImportPojo> act) {
		System.out.println("全部信息如下 ===================================");
		System.out.println(act);
		a2 = System.currentTimeMillis() - a1;
		System.out.println("耗时（s）：" + a2 / 1000);
		System.out.println("耗时（ms）：" + a2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//@Test
	public void createExcelTest() {
		
		File file = new File("exl/create.xlsx");
		
		Init<CreateExcelPOJO> init = new Init<CreateExcelPOJO>(file, CreateExcelPOJO.class, true);
		ActionContext<CreateExcelPOJO> act = init.start();
		
		List<CreateExcelPOJO> list = act.getSheets().get(0).getInfo();
		
		CreateExcel<CreateExcelPOJO> c = new CreateExcel<CreateExcelPOJO>();
		try {
			c.create(list, "这是一个sheet名");
			// 可以使用该方法拿到hssfWorkbook自行处理
			// HSSFWorkbook hssfWorkbook = c.getHSSFWorkbook();
			c.write(new File("excel.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
