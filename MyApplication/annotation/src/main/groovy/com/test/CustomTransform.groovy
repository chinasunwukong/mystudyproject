//package com.test
//
//import com.android.build.api.transform.DirectoryInput
//import com.android.build.api.transform.JarInput
//import com.android.build.api.transform.QualifiedContent
//import com.android.build.api.transform.Transform
//import com.android.build.api.transform.TransformException
//import com.android.build.api.transform.TransformInput
//import com.android.build.api.transform.TransformInvocation
//import com.android.build.api.transform.TransformOutputProvider;
//
//public class CustomTransform extends Transform{
//
//    @Override
//    String getName() {
//        return null
//    }
//
//    @Override
//    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
//        super.transform(transformInvocation)
//
//        boolean isIncremental = transformInvocation.isIncremental();
//        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
//        Collection<TransformInput> inputs = transformInvocation.getInputs();
//        //引用型输入，无需输出。
//        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();
//        //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
//        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
//        for(TransformInput input : inputs) {
//            for(JarInput jarInput : input.getJarInputs()) {
//                File dest = outputProvider.getContentLocation(
//                        jarInput.getFile().getAbsolutePath(),
//                        jarInput.getContentTypes(),
//                        jarInput.getScopes(),
//                        Format.JAR);
//                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
//                FileUtils.copyFile(jarInput.getFile(), dest);
//            }
//            for(DirectoryInput directoryInput : input.getDirectoryInputs()) {
//                File dest = outputProvider.getContentLocation(directoryInput.getName(),
//                        directoryInput.getContentTypes(), directoryInput.getScopes(),
//                        Format.DIRECTORY);
//                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
//                FileUtils.copyDirectory(directoryInput.getFile(), dest);
//            }
//        }
//    }
//
//    @Override
//    Set<QualifiedContent.ContentType> getInputTypes() {
//        return null
//    }
//
//    @Override
//    Set<? super QualifiedContent.Scope> getScopes() {
//        return null
//    }
//
//    @Override
//    boolean isIncremental() {
//        return false
//    }
//}