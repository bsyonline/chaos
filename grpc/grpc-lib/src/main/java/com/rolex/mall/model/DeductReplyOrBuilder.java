// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MallModel.proto

package com.rolex.mall.model;

public interface DeductReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.rolex.mall.DeductReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 返回码
   * </pre>
   *
   * <code>int32 code = 1;</code>
   * @return The code.
   */
  int getCode();

  /**
   * <pre>
   * 描述信息
   * </pre>
   *
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <pre>
   * 描述信息
   * </pre>
   *
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();
}
