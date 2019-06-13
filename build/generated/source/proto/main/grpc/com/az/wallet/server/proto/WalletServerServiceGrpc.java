package com.az.wallet.server.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.21.0)",
    comments = "Source: Wallet.proto")
public final class WalletServerServiceGrpc {

  private WalletServerServiceGrpc() {}

  public static final String SERVICE_NAME = "com.az.wallet.server.proto.WalletServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest,
      com.az.wallet.server.proto.WalletResponse> getDepositMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Deposit",
      requestType = com.az.wallet.server.proto.WalletRequest.class,
      responseType = com.az.wallet.server.proto.WalletResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest,
      com.az.wallet.server.proto.WalletResponse> getDepositMethod() {
    io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest, com.az.wallet.server.proto.WalletResponse> getDepositMethod;
    if ((getDepositMethod = WalletServerServiceGrpc.getDepositMethod) == null) {
      synchronized (WalletServerServiceGrpc.class) {
        if ((getDepositMethod = WalletServerServiceGrpc.getDepositMethod) == null) {
          WalletServerServiceGrpc.getDepositMethod = getDepositMethod = 
              io.grpc.MethodDescriptor.<com.az.wallet.server.proto.WalletRequest, com.az.wallet.server.proto.WalletResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.az.wallet.server.proto.WalletServerService", "Deposit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.az.wallet.server.proto.WalletRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.az.wallet.server.proto.WalletResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletServerServiceMethodDescriptorSupplier("Deposit"))
                  .build();
          }
        }
     }
     return getDepositMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest,
      com.az.wallet.server.proto.WalletResponse> getWithdrawMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Withdraw",
      requestType = com.az.wallet.server.proto.WalletRequest.class,
      responseType = com.az.wallet.server.proto.WalletResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest,
      com.az.wallet.server.proto.WalletResponse> getWithdrawMethod() {
    io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest, com.az.wallet.server.proto.WalletResponse> getWithdrawMethod;
    if ((getWithdrawMethod = WalletServerServiceGrpc.getWithdrawMethod) == null) {
      synchronized (WalletServerServiceGrpc.class) {
        if ((getWithdrawMethod = WalletServerServiceGrpc.getWithdrawMethod) == null) {
          WalletServerServiceGrpc.getWithdrawMethod = getWithdrawMethod = 
              io.grpc.MethodDescriptor.<com.az.wallet.server.proto.WalletRequest, com.az.wallet.server.proto.WalletResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.az.wallet.server.proto.WalletServerService", "Withdraw"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.az.wallet.server.proto.WalletRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.az.wallet.server.proto.WalletResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletServerServiceMethodDescriptorSupplier("Withdraw"))
                  .build();
          }
        }
     }
     return getWithdrawMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest,
      com.az.wallet.server.proto.WalletResponse> getBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Balance",
      requestType = com.az.wallet.server.proto.WalletRequest.class,
      responseType = com.az.wallet.server.proto.WalletResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest,
      com.az.wallet.server.proto.WalletResponse> getBalanceMethod() {
    io.grpc.MethodDescriptor<com.az.wallet.server.proto.WalletRequest, com.az.wallet.server.proto.WalletResponse> getBalanceMethod;
    if ((getBalanceMethod = WalletServerServiceGrpc.getBalanceMethod) == null) {
      synchronized (WalletServerServiceGrpc.class) {
        if ((getBalanceMethod = WalletServerServiceGrpc.getBalanceMethod) == null) {
          WalletServerServiceGrpc.getBalanceMethod = getBalanceMethod = 
              io.grpc.MethodDescriptor.<com.az.wallet.server.proto.WalletRequest, com.az.wallet.server.proto.WalletResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.az.wallet.server.proto.WalletServerService", "Balance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.az.wallet.server.proto.WalletRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.az.wallet.server.proto.WalletResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletServerServiceMethodDescriptorSupplier("Balance"))
                  .build();
          }
        }
     }
     return getBalanceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WalletServerServiceStub newStub(io.grpc.Channel channel) {
    return new WalletServerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WalletServerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WalletServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WalletServerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class WalletServerServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public void deposit(com.az.wallet.server.proto.WalletRequest request,
        io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDepositMethod(), responseObserver);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public void withdraw(com.az.wallet.server.proto.WalletRequest request,
        io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getWithdrawMethod(), responseObserver);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public void balance(com.az.wallet.server.proto.WalletRequest request,
        io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getBalanceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDepositMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.az.wallet.server.proto.WalletRequest,
                com.az.wallet.server.proto.WalletResponse>(
                  this, METHODID_DEPOSIT)))
          .addMethod(
            getWithdrawMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.az.wallet.server.proto.WalletRequest,
                com.az.wallet.server.proto.WalletResponse>(
                  this, METHODID_WITHDRAW)))
          .addMethod(
            getBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.az.wallet.server.proto.WalletRequest,
                com.az.wallet.server.proto.WalletResponse>(
                  this, METHODID_BALANCE)))
          .build();
    }
  }

  /**
   */
  public static final class WalletServerServiceStub extends io.grpc.stub.AbstractStub<WalletServerServiceStub> {
    private WalletServerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletServerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletServerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletServerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public void deposit(com.az.wallet.server.proto.WalletRequest request,
        io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDepositMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public void withdraw(com.az.wallet.server.proto.WalletRequest request,
        io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getWithdrawMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public void balance(com.az.wallet.server.proto.WalletRequest request,
        io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBalanceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WalletServerServiceBlockingStub extends io.grpc.stub.AbstractStub<WalletServerServiceBlockingStub> {
    private WalletServerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletServerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletServerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletServerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public com.az.wallet.server.proto.WalletResponse deposit(com.az.wallet.server.proto.WalletRequest request) {
      return blockingUnaryCall(
          getChannel(), getDepositMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public com.az.wallet.server.proto.WalletResponse withdraw(com.az.wallet.server.proto.WalletRequest request) {
      return blockingUnaryCall(
          getChannel(), getWithdrawMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public com.az.wallet.server.proto.WalletResponse balance(com.az.wallet.server.proto.WalletRequest request) {
      return blockingUnaryCall(
          getChannel(), getBalanceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WalletServerServiceFutureStub extends io.grpc.stub.AbstractStub<WalletServerServiceFutureStub> {
    private WalletServerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletServerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletServerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletServerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.az.wallet.server.proto.WalletResponse> deposit(
        com.az.wallet.server.proto.WalletRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDepositMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.az.wallet.server.proto.WalletResponse> withdraw(
        com.az.wallet.server.proto.WalletRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getWithdrawMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Unary gRPC for futureStub request to server for Concurrency
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.az.wallet.server.proto.WalletResponse> balance(
        com.az.wallet.server.proto.WalletRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getBalanceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DEPOSIT = 0;
  private static final int METHODID_WITHDRAW = 1;
  private static final int METHODID_BALANCE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WalletServerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WalletServerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DEPOSIT:
          serviceImpl.deposit((com.az.wallet.server.proto.WalletRequest) request,
              (io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse>) responseObserver);
          break;
        case METHODID_WITHDRAW:
          serviceImpl.withdraw((com.az.wallet.server.proto.WalletRequest) request,
              (io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse>) responseObserver);
          break;
        case METHODID_BALANCE:
          serviceImpl.balance((com.az.wallet.server.proto.WalletRequest) request,
              (io.grpc.stub.StreamObserver<com.az.wallet.server.proto.WalletResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WalletServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.az.wallet.server.proto.Wallet.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WalletServerService");
    }
  }

  private static final class WalletServerServiceFileDescriptorSupplier
      extends WalletServerServiceBaseDescriptorSupplier {
    WalletServerServiceFileDescriptorSupplier() {}
  }

  private static final class WalletServerServiceMethodDescriptorSupplier
      extends WalletServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WalletServerServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WalletServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WalletServerServiceFileDescriptorSupplier())
              .addMethod(getDepositMethod())
              .addMethod(getWithdrawMethod())
              .addMethod(getBalanceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
