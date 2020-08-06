package com.kwg.annotation_compiler;

import com.google.auto.service.AutoService;
import com.kwg.annotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)  //@SupportedAnnotationTypes({"com...BindPath.class",})   @SupportedSourceVersion(1.8)
public class AnnotationCompiler extends AbstractProcessor {

    //生成文件的对象
    Filer filer;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    //声明注解支持的java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }


    //声名注解要处理的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }


    //关键
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //获取当前模块用到了的BindPath的节点
        //TypeElement类节点
        //ExecutableElement方法节点
        //VariableElement 成员变量的节点

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        Map<String,String> map = new HashMap<>();
        for (Element element: elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            //获取Activity 上面的BindPath注解
            BindPath antation = typeElement.getAnnotation(BindPath.class);
            String key  = antation.value();
            //获取包名加类名
            Name activityName = typeElement.getQualifiedName();
            map.put(key,activityName+".class");
        }

        //写文件
        if(map.size()>0){
            Writer writer = null;
            //需要生成类名
            String activityName = "Activity"+System.currentTimeMillis();
            //生成一个java文件
            try {
                JavaFileObject sourceFile = filer.createSourceFile("com.kwg.util." + activityName);
                writer =   sourceFile.openWriter();
                StringBuffer  stringBuffer = new StringBuffer();
                stringBuffer.append("package com.kwg.util;\n");
                stringBuffer.append("import com.kwg.arouter.ARouter;\n" +
                        "import com.kwg.arouter.IRouter;\n" +
                        "\n" +
                        "public class "+activityName+" implements IRouter {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    String className = map.get(key);
                    stringBuffer.append("ARouter.getInstance().addActivity("+"\""+key+"\""+","+className+");\n");
                    stringBuffer.append("}\n" +
                            "}");
                    writer.write(stringBuffer.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return false;
    }
}
