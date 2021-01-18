//package mytest.dynamicproxy;
//
//import sun.security.action.GetBooleanAction;
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.security.AccessController;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * @BelongsProject: spring
// * @BelongsPackage: mytest.dynamicproxy
// * @Author: Lovezly
// * @CreateTime: 2020-07-26 15:12
// * @Description:
// */
//public class ProxyGenerator {
//
//	private static final int CLASSFILE_MAJOR_VERSION = 49;
//	private static final int CLASSFILE_MINOR_VERSION = 0;
//	private static final int CONSTANT_UTF8 = 1;
//	private static final int CONSTANT_UNICODE = 2;
//	private static final int CONSTANT_INTEGER = 3;
//	private static final int CONSTANT_FLOAT = 4;
//	private static final int CONSTANT_LONG = 5;
//	private static final int CONSTANT_DOUBLE = 6;
//	private static final int CONSTANT_CLASS = 7;
//	private static final int CONSTANT_STRING = 8;
//	private static final int CONSTANT_FIELD = 9;
//	private static final int CONSTANT_METHOD = 10;
//	private static final int CONSTANT_INTERFACEMETHOD = 11;
//	private static final int CONSTANT_NAMEANDTYPE = 12;
//	private static final int ACC_PUBLIC = 1;
//	private static final int ACC_PRIVATE = 2;
//	private static final int ACC_STATIC = 8;
//	private static final int ACC_FINAL = 16;
//	private static final int ACC_SUPER = 32;
//	private static final int opc_aconst_null = 1;
//	private static final int opc_iconst_0 = 3;
//	private static final int opc_bipush = 16;
//	private static final int opc_sipush = 17;
//	private static final int opc_ldc = 18;
//	private static final int opc_ldc_w = 19;
//	private static final int opc_iload = 21;
//	private static final int opc_lload = 22;
//	private static final int opc_fload = 23;
//	private static final int opc_dload = 24;
//	private static final int opc_aload = 25;
//	private static final int opc_iload_0 = 26;
//	private static final int opc_lload_0 = 30;
//	private static final int opc_fload_0 = 34;
//	private static final int opc_dload_0 = 38;
//	private static final int opc_aload_0 = 42;
//	private static final int opc_astore = 58;
//	private static final int opc_astore_0 = 75;
//	private static final int opc_aastore = 83;
//	private static final int opc_pop = 87;
//	private static final int opc_dup = 89;
//	private static final int opc_ireturn = 172;
//	private static final int opc_lreturn = 173;
//	private static final int opc_freturn = 174;
//	private static final int opc_dreturn = 175;
//	private static final int opc_areturn = 176;
//	private static final int opc_return = 177;
//	private static final int opc_getstatic = 178;
//	private static final int opc_putstatic = 179;
//	private static final int opc_getfield = 180;
//	private static final int opc_invokevirtual = 182;
//	private static final int opc_invokespecial = 183;
//	private static final int opc_invokestatic = 184;
//	private static final int opc_invokeinterface = 185;
//	private static final int opc_new = 187;
//	private static final int opc_anewarray = 189;
//	private static final int opc_athrow = 191;
//	private static final int opc_checkcast = 192;
//	private static final int opc_wide = 196;
//	private static final String superclassName = "java/lang/reflect/Proxy";
//	private static final String handlerFieldName = "h";
//	private static final boolean saveGeneratedFiles = (Boolean) AccessController.doPrivileged(new GetBooleanAction("sun.misc.ProxyGenerator.saveGeneratedFiles"));
//	private static Method hashCodeMethod;
//	private static Method equalsMethod;
//	private static Method toStringMethod;
//	private String className;
//	private Class<?>[] interfaces;
//	private int accessFlags;
//	private sun.misc.ProxyGenerator.ConstantPool cp = new sun.misc.ProxyGenerator.ConstantPool();
//	private List<sun.misc.ProxyGenerator.FieldInfo> fields = new ArrayList();
//	private List<sun.misc.ProxyGenerator.MethodInfo> methods = new ArrayList();
//	private Map<String, List<sun.misc.ProxyGenerator.ProxyMethod>> proxyMethods = new HashMap();
//	private int proxyMethodCount = 0;
//
//
//	/**
//	 * Map<String, List<ProxyGenerator.ProxyMethod>> proxyMethods = new HashMap(); 用来存储方法签名  及其对应的方法描述
//	 * addProxyMethod方法下面有详解
//	 *
//	 * @return
//	 */
//	private byte[] generateClassFile() {
//
//		//添加Object的hashcode方法
//		this.addProxyMethod(hashCodeMethod, Object.class);
//		//添加Object的equals方法
//		this.addProxyMethod(equalsMethod, Object.class);
//		//添加Object的toString方法
//		this.addProxyMethod(toStringMethod, Object.class);
//		Class[] interfaces = this.interfaces;
//		//获取到接口数组的长度
//		int interLength = interfaces.length;
//
//		int index;
//		Class in;
//		for (index = 0; index < interLength; ++index) {
//			in = interfaces[index];
//			//迭代获取到当前接口的所有方法
//			Method[] methods = in.getMethods();
//			int methodLength = methods.length;
//			//迭代所有方法
//			for (int methodIndex = 0; methodIndex < methodLength; ++methodIndex) {
//				Method m = methods[methodIndex];
//				//添加方法签名与方法描述
//				this.addProxyMethod(m, in);
//			}
//		}
//		/************上面则就将所有的方法签名与方法描述都存入到了proxyMethods中********************/
//		Iterator methodsIterator = this.proxyMethods.values().iterator();
//
//		List meth;
//		while (methodsIterator.hasNext()) {
//			//迭代每个方法签名的值 （List<ProxyGenerator.ProxyMethod>）
//			meth = (List) methodsIterator.next();
//			//检查方法签名值一样的方法返回值是否相同
//			checkReturnTypes(meth);
//		}
//
//		Iterator me;
//		try {
//			//添加构造方法  并将构造方法的二进制代码写入methodInfo中的ByteArrayOutputStream --》对应字节码文件中的有参构造
//			this.methods.add(this.generateConstructor());
//			//获取方法签名对应迭代器
//			methodsIterator = this.proxyMethods.values().iterator();
//
//			while (methodsIterator.hasNext()) {
//				meth = (List) methodsIterator.next();
//				me = meth.iterator();
//				//获取到每个方法签名中对应描述列表的迭代器
//				while (me.hasNext()) {
//					//获取到方法信息
//					ProxyGenerator.ProxyMethod proMe = (ProxyGenerator.ProxyMethod) me.next();
//					//在feilds添加方法对应参数名字 签名，以及访问修饰符的FiledInfo
//					this.fields.add(new ProxyGenerator.FieldInfo(proMe.methodFieldName, "Ljava/lang/reflect/Method;", 10));
//					//添加对应的方法  --》对应字节码文件中的代理类接口实现方法
//					this.methods.add(proMe.generateMethod());
//				}
//			}
//			//添加对应的静态方法  --》对应字节码文件中的static方法
//			this.methods.add(this.generateStaticInitializer());
//		} catch (IOException var10) {
//			throw new InternalError("unexpected I/O Exception", var10);
//		}
//		//做一些方法和参数数量校验
//		if (this.methods.size() > 65535) {
//			throw new IllegalArgumentException("method limit exceeded");
//		} else if (this.fields.size() > 65535) {
//			throw new IllegalArgumentException("field limit exceeded");
//		} else {
//			//字节码常量池中的符号引用  保存类全限定名
//			this.cp.getClass(dotToSlash(this.className));
//			//常量池中符号引用保存继承的Proxy类的权限定名
//			this.cp.getClass("java/lang/reflect/Proxy");
//			//获取到所有的接口
//			interfaces = this.interfaces;
//			//获取到接口的长度
//			interLength = interfaces.length;
//			//遍历接口
//			for (index = 0; index < interLength; ++index) {
//				in = interfaces[index];
//				//常量池中符号引用保存接口的全限定定名
//				this.cp.getClass(dotToSlash(in.getName()));
//			}
//			//常量池符号引用中保存访问标志
//			this.cp.setReadOnly();
//			//创建输出流
//			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
//			DataOutputStream outputStream = new DataOutputStream(byteOutputStream);
//
//			try {
//				//1.写入java魔数4个字节 对应16进制数据为JAVA魔数CA FE BA BE
//				outputStream.writeInt(-889275714);
//				//2..写入小版本号2个字节
//				outputStream.writeShort(0);
//				//3.写入大版本号2个字节
//				outputStream.writeShort(49);
//				//4.写入常量池计数2个字节 5.以及上面添加的常量池
//				this.cp.write(outputStream);
//				//6.设置访问标志 2个字节
//				outputStream.writeShort(this.accessFlags);
//				//7.设置类索引2个字节
//				outputStream.writeShort(this.cp.getClass(dotToSlash(this.className)));
//				//8.设置父类索引 2个字节   这儿继承Proxy类  所以只能代理接口 因为java单继承
//				outputStream.writeShort(this.cp.getClass("java/lang/reflect/Proxy"));
//				//9.设置接口长度 2个字节  所以前面要判断接口数量不能大于65535
//				outputStream.writeShort(this.interfaces.length);
//				Class[] interfacess = this.interfaces;
//				int interfacelength = interfacess.length;
//
//				for (int index = 0; index < interfacelength; ++index) {
//					Class c = interfacess[index];
//					//10.写入接口索引 2个字节
//					outputStream.writeShort(this.cp.getClass(dotToSlash(c.getName())));
//				}
//				//11.写入变量数量 2个字节
//				outputStream.writeShort(this.fields.size());
//				me = this.fields.iterator();
//
//				while (me.hasNext()) {
//					ProxyGenerator.FieldInfo p = (ProxyGenerator.FieldInfo) me.next();
//					//12.写入变量
//					p.write(outputStream);
//				}
//				//13.写入方法数量 2个字节
//				outputStream.writeShort(this.methods.size());
//				me = this.methods.iterator();
//
//				while (me.hasNext()) {
//					ProxyGenerator.MethodInfo proMe = (ProxyGenerator.MethodInfo) me.next();
//					//14.写入方法
//					proMe.write(outputStream);
//				}
//				//15.没有属性表  直接写0
//				outputStream.writeShort(0);
//				//返回一个完整class的字节码
//				return byteOutputStream.toByteArray();
//			} catch (IOException var9) {
//				throw new InternalError("unexpected I/O Exception", var9);
//			}
//		}
//	}
//
//
//	private void addProxyMethod(Method method, Class<?> class) {
//		//获取到方法名
//		String methodName = method.getName();
//		//获取所有方法参数类型
//		Class[] methodParamTypes = method.getParameterTypes();
//		//获取返回类型
//		Class returnTypes = method.getReturnType();
//		//获取所有的异常
//		Class[] exceptions = method.getExceptionTypes();
//		//方法名+方法参数类形的方法签名  用来标识唯一的方法
//		String methodSign = methodName + getParameterDescriptors(methodParamTypes);
//		//获取该方法的List<ProxyGenerator.ProxyMethod>
//		Object me = (List) this.proxyMethods.get(methodSign);
//		if (me != null) {
//			//如果不为空 即出现了相同方法签名的方法
//			Iterator iter = ((List) me).iterator();
//			//则获得这个list的迭代器
//			while (iter.hasNext()) {
//				//开始迭代
//				ProxyGenerator.ProxyMethod proxyMe = (ProxyGenerator.ProxyMethod) iter.next();
//				//如果这个方法签名对应的方法描述中返回值也与传入的一致
//				if (returnTypes == proxyMe.returnType) {
//					//则直接合并两个方法中抛出的所有异常然后直接返回
//					ArrayList methodList = new ArrayList();
//					collectCompatibleTypes(exceptions, proxyMe.exceptionTypes, methodList);
//					collectCompatibleTypes(proxyMe.exceptionTypes, exceptions, methodList);
//					proxyMe.exceptionTypes = new Class[methodList.size()];
//					proxyMe.exceptionTypes = (Class[]) methodList.toArray(proxyMe.exceptionTypes);
//					return;
//				}
//			}
//		}
//		//如果为空
//		else {
//			//则新建一个List
//			me = new ArrayList(3);
//			//将方法签名 与这个新建的List<ProxyGenerator.ProxyMethod> 方法描述 put进去
//			this.proxyMethods.put(methodSign, me);
//		}
//		//然后再这个list中新添加一个ProxyMethod 方法描述  这个Proxymethod方法包含了传入的方法的所有特征
//		((List) me).add(new ProxyGenerator.ProxyMethod(methodName, methodParamTypes, returnTypes, exceptions, class));
//	}
//
//
//	private static String getParameterDescriptors(Class<?>[] var0) {
//		StringBuilder var1 = new StringBuilder("(");
//
//		for(int var2 = 0; var2 < var0.length; ++var2) {
//			var1.append(getFieldType(var0[var2]));
//		}
//
//		var1.append(')');
//		return var1.toString();
//	}
//
//	private static String getFieldType(Class<?> var0) {
//		if (var0.isPrimitive()) {
//			return sun.misc.ProxyGenerator.PrimitiveTypeInfo.get(var0).baseTypeString;
//		} else {
//			return var0.isArray() ? var0.getName().replace('.', '/') : "L" + dotToSlash(var0.getName()) + ";";
//		}
//	}
//}
