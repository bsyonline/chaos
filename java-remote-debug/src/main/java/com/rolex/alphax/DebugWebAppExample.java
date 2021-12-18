/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.alphax;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ClassType;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.tools.jdi.SocketAttachingConnector;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author rolex
 * @since 2021
 */
public class DebugWebAppExample {
    private static VirtualMachine vm;
    private static EventRequestManager eventRequestManager;
    private static EventQueue eventQueue;
    private static EventSet eventSet;
    private static boolean vmExit = false;
    private static String className = "com.rolex.alphax.remote.debug.controller.HelloController";
    private static int breakPoint = 17;

    public static void main(String[] args) throws Exception {
        launchDebuggee();
        registerEvent();
        eventLoop();
    }

    public static void launchDebuggee() throws IOException, IllegalConnectorArgumentsException {
        List<AttachingConnector> connectors = Bootstrap.virtualMachineManager().attachingConnectors();
        SocketAttachingConnector socketAttachingConnector = null;
        for (AttachingConnector ac : connectors) {
            if (ac instanceof SocketAttachingConnector) {
                socketAttachingConnector = (SocketAttachingConnector) ac;
                break;
            }
        }

        if (socketAttachingConnector == null) {
            throw new IllegalStateException("Could not find socket connector");
        }
        Map<String, Connector.Argument> defaultArguments = socketAttachingConnector.defaultArguments();
        defaultArguments.get("port").setValue("5005");
        vm = socketAttachingConnector.attach(defaultArguments);
    }

    public static void registerEvent() throws AbsentInformationException {
        // Register ClassPrepareRequest
        eventRequestManager = vm.eventRequestManager();
        System.out.println("registerEvent -> 注册MethodEntryRequest事件");
        MethodEntryRequest entryReq = eventRequestManager.createMethodEntryRequest();
        entryReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        entryReq.addClassFilter(className);
        entryReq.enable();

        System.out.println("registerEvent -> 注册MethodExitRequest事件");
        MethodExitRequest exitReq = eventRequestManager.createMethodExitRequest();
        exitReq.addClassFilter(className);
        exitReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        exitReq.enable();

        System.out.println("registerEvent -> 注册ClassPrepareRequest事件");
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(className);
        classPrepareRequest.addCountFilter(1);
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();

    }

    private static void eventLoop() throws Exception {
        eventQueue = vm.eventQueue();
        while (true) {
            if (vmExit == true) {
                System.out.println("eventLoop -> vmexit");
                break;
            }
            eventSet = eventQueue.remove();
            EventIterator eventIterator = eventSet.eventIterator();
            while (eventIterator.hasNext()) {
                Event event = (Event) eventIterator.next();
                execute(event);
                if (!vmExit) {
                    eventSet.resume();
                }
            }
        }
    }

    private static void execute(Event event) throws Exception {
        if (event instanceof VMStartEvent) {
            System.out.println("eventLoop -> VM started");
        } else if (event instanceof MethodEntryEvent) {
            Method method = ((MethodEntryEvent) event).method();
            System.out.printf("eventLoop -> Enter Method: %s, Signature:%s\n", method.name(), method.signature());
            System.out.printf("eventLoop -> ReturnType:%s\n", method.returnTypeName());
        } else if (event instanceof MethodExitEvent) {
            Method method = ((MethodExitEvent) event).method();
            System.out.printf("eventLoop -> Exit method: %s\n", method.name());
        } else if (event instanceof ClassPrepareEvent) {
            ClassPrepareEvent evt = (ClassPrepareEvent) event;
            ClassType classType = (ClassType) evt.referenceType();
            System.out.printf("eventLoop -> 添加断点：com.rolex.alphax.HelloWorld(L%d)\n", breakPoint);
            Location location = classType.locationsOfLine(breakPoint).get(0);
            System.out.println("eventLoop -> 注册ClassPrepareRequest事件");
            BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
            breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
            breakpointRequest.enable();
        } else if (event instanceof LocatableEvent) {
            System.out.println("eventLoop -> LocatableEvent = " + event);
            if (event instanceof BreakpointEvent) {
                System.out.printf("eventLoop -> 到达断点：com.rolex.alphax.HelloWorld(L%d)\n", breakPoint);
                BreakpointEvent breakpointEvent = (BreakpointEvent) event;
                ThreadReference threadReference = breakpointEvent.thread();
                StackFrame stackFrame = threadReference.frame(0);
                List<LocalVariable> localVariables = stackFrame.visibleVariables();
                System.out.println("eventLoop -> 局部变量:[");
                for (LocalVariable localVariable : localVariables) {
                    System.out.println("\t\t\t\t变量名=" + localVariable.name()
                            + ", 变量类型=" + localVariable.typeName()
                            + ", 变量值=" + stackFrame.getValue(localVariable));
                }
                System.out.println("\t\t\t ]");
            }
        } else if (event instanceof VMDisconnectEvent) {
            vmExit = true;
        }
    }
}
