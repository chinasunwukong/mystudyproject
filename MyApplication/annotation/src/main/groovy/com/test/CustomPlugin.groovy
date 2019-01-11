//package com.test
//
//import com.android.build.gradle.AppExtension
//import com.android.build.gradle.external.cmake.server.Project
//import org.gradle.api.Plugin;
//
//
//public class CustomPlugin implements Plugin<Project> {
//
//    @Override
//    void apply(Project project) {
//
//        AppExtension appExtension=project.getProperties().get("android");
//        appExtension.registerTransform(new CustomTransform(),Collections.EMPTY_LIST);
//
//    }
//}