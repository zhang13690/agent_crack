package com.ac.idea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.instrument.Instrumentation;

public class CrackIdea {

    private static boolean watcherFlag = true;

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("**********    util.jar; Addition of WindowWatcher");

        // 新建线程运行
        new Thread(() -> {
            while (watcherFlag) {
                try {
                    System.out.println("进入 WindowWatcher 循环...");
                    Thread.sleep(500L);
                    // 遍历 Windows
                    for (Window window : Window.getWindows()) {
                        if (window instanceof JDialog) {
                            final JDialog dialog = (JDialog) window;
                            if ("Licenses".equals(dialog.getTitle())) {

                                for (WindowListener windowListener : dialog.getWindowListeners()) {
                                    dialog.removeWindowListener(windowListener);
                                }

                                dialog.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        // Messages.showInfoMessage("后续请勿打开 Help - Register 菜单", "提醒");
                                        dialog.dispose();
                                    }

                                });

                                dialog.setTitle("若要继续试用，请点击*右上角*关闭按钮");

                                // 设置标记变量为 false，表示无需再循环设置此试用代码了
                                watcherFlag = false;
                                // 后续不用执行
                                break;
                            }

                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("**********    util.jar; WindowWatcher Execution Done!");
        }).start();

    }

}
