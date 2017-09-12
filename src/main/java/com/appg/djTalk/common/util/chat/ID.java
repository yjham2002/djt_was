package com.appg.djTalk.common.util.chat;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.binary.Base64;

public class ID{

	private static AtomicInteger	seq	= new AtomicInteger(0);

	/**
	 * Atomic 시퀀스 반환 - Short Max 시 0
	 * 
	 * @return 시퀀스
	 */
	private static short getSeq()
	{

		seq.compareAndSet(Short.MAX_VALUE, 0);

		short ret = (short) seq.incrementAndGet();

		return ret;
	}

	/**
	 * Nano Milliseconds 반환
	 * 
	 * @return
	 */
	private static long getNano()
	{
		long millis = System.currentTimeMillis() * 1000000;
		long nanoTime = System.nanoTime() % 10000000;

		return millis + nanoTime;
	}

	/**
	 * 트랜잭션 ID 생성
	 * 
	 * @param platform
	 * @param uid
	 * @return
	 */
	public static String generateTransactionId()
	{

		Random rnd = new Random(Integer.MAX_VALUE);
		short rSrt = (short) rnd.nextInt((int) Short.MAX_VALUE);
		int rInt = rnd.nextInt();

		ByteBuffer b = ByteBuffer.allocate(16);
		b.putShort(rSrt);
		b.putInt(rInt);
		b.putLong(getNano());
		b.putShort(getSeq());

		// String id = new String(b.array(), Charset.forName("UTF-8")) ;
		String id = new String(Base64.encodeBase64(b.array()), Charset.forName("UTF-8"));
		return id;
	}

	/**
	 * 멀티룸 ID 생성
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	public static String generateMultiRoomId(short pid, String uid)
	{
		return generateMultiRoomId(pid, uid, 0, 0);
	}

	/**
	 * 멀티룸 ID 생성
	 * 
	 * @param pid
	 * @param uid
	 * @param extra1
	 * @return
	 */
	public static String generateMultiRoomId(short pid, String uid, int extra1)
	{
		return generateMultiRoomId(pid, uid, extra1, 0);
	}

	/**
	 * 멀티룸 ID 생성
	 * 
	 * @param pid
	 * @param uid
	 * @param extra1
	 * @param extra2
	 * @return
	 */
	public static String generateMultiRoomId(short pid, String uid, int extra1, int extra2)
	{

		byte[] uidBytes = uid.getBytes(Charset.forName("UTF-8"));

		int allocate = ((Short.SIZE + Short.SIZE + (Integer.SIZE * 3) + Long.SIZE) / Byte.SIZE) + uidBytes.length;

		Random rnd = new Random(Integer.MAX_VALUE);
		int rInt = rnd.nextInt();

		ByteBuffer b = ByteBuffer.allocate(allocate);
		b.putInt(extra1);
		b.putInt(extra2);
		b.putShort(pid);
		b.putInt(rInt);
		b.putLong(getNano());
		b.putShort(getSeq());
		b.put(uidBytes);

		// String id = new String(b.array(), Charset.forName("UTF-8")) ;
		String id = new String(Base64.encodeBase64(b.array()), Charset.forName("UTF-8"));
		return id;
	}

	/**
	 * 1:1룸 ID 생성
	 * 
	 * @param uid1
	 * @param uid2
	 * @return
	 */
	public static String generateSingleRoomId(String uid1, String uid2)
	{
		return generateSingleRoomId(uid1, uid2, 0);
	}

	/**
	 * 1:1룸 ID 생성
	 * 
	 * @param uid1
	 * @param uid2
	 * @param extra1
	 * @return
	 */
	public static String generateSingleRoomId(String uid1, String uid2, int extra1)
	{
		return generateSingleRoomId(uid1, uid2, extra1, 0);
	}

	/**
	 * 1:1룸 ID 생성
	 * 
	 * @param uid1
	 * @param uid2
	 * @param extra1
	 * @param extra2
	 * @return
	 */
	public static String generateSingleRoomId(String uid1, String uid2, int extra1, int extra2)
	{

		int comp = uid1.compareTo(uid2);

		String min = (comp > 0) ? uid2 : uid1;
		String max = (comp > 0) ? uid1 : uid2;

		byte[] minBytes = min.getBytes(Charset.forName("UTF-8"));
		byte[] maxBytes = max.getBytes(Charset.forName("UTF-8"));

		int allocate = (((Integer.SIZE * 2) + (Short.SIZE * 2)) / Byte.SIZE) + minBytes.length + maxBytes.length;

		ByteBuffer b = ByteBuffer.allocate(allocate);
		b.putInt(extra1);
		b.putInt(extra2);
		b.putShort((short) minBytes.length);
		b.put(minBytes);
		b.putShort((short) maxBytes.length);
		b.put(maxBytes);

		// String id = Base64.encodeBase64String(b.array());
		String id = new String(Base64.encodeBase64(b.array()), Charset.forName("UTF-8"));

		return id;
	}

}