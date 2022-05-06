// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MsgProto.proto

package com.rolex.rpc.model.proto;

public interface MsgProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.rolex.grpc.MsgProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 jobId = 1;</code>
   * @return The jobId.
   */
  long getJobId();

  /**
   * <code>.com.rolex.grpc.MsgProto.CommandType type = 2;</code>
   * @return The enum numeric value on the wire for type.
   */
  int getTypeValue();
  /**
   * <code>.com.rolex.grpc.MsgProto.CommandType type = 2;</code>
   * @return The type.
   */
  com.rolex.rpc.model.proto.MsgProto.CommandType getType();

  /**
   * <code>.com.rolex.grpc.MsgProto.ExecutorType executorType = 3;</code>
   * @return The enum numeric value on the wire for executorType.
   */
  int getExecutorTypeValue();
  /**
   * <code>.com.rolex.grpc.MsgProto.ExecutorType executorType = 3;</code>
   * @return The executorType.
   */
  com.rolex.rpc.model.proto.MsgProto.ExecutorType getExecutorType();

  /**
   * <code>string host = 4;</code>
   * @return The host.
   */
  java.lang.String getHost();
  /**
   * <code>string host = 4;</code>
   * @return The bytes for host.
   */
  com.google.protobuf.ByteString
      getHostBytes();

  /**
   * <code>int32 port = 5;</code>
   * @return The port.
   */
  int getPort();
}
