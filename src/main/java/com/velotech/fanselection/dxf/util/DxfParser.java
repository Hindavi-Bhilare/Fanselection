
package com.velotech.fanselection.dxf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class DxfParser {

	public static void parseDXF(File file, OutputStream fileOutputStream, Map<String, String> blockValues, Map<String, Boolean> layerValues,
			List<String> attachedFiles) {

		try {
			String codeString = null;
			String value = null;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			while ((codeString = bufferedReader.readLine()) != null && (value = bufferedReader.readLine()) != null) {
				fileOutputStream.write(codeString.getBytes());
				fileOutputStream.write("\n".getBytes());
				// @@@@@@ Start Mithlesh Singh
				if (value.contains(".jpg") || value.contains(".png")) {
					if (!attachedFiles.isEmpty()) {
						for (int i = 0; i < attachedFiles.size(); i++) {

							if (value.contains(attachedFiles.get(i)))
								fileOutputStream.write(blockValues.get(attachedFiles.get(i)).getBytes());

						}
					}
					if (value.contains("perfChart.png"))
						fileOutputStream.write(blockValues.get("perfChart.png").getBytes());

				} else
					fileOutputStream.write(value.getBytes());
				// @@@@@@ End Mithlesh Singh
				fileOutputStream.write("\n".getBytes());
				codeString = codeString.replaceAll(" ", "");
				// int code = Integer.parseInt(codeString);

				if ("100".equals(codeString) && "AcDbBlockReference".equals(value.replaceAll(" ", ""))) {

					for (; (codeString = bufferedReader.readLine()) != null && !"2".equals(codeString.replaceAll(" ", "")); fileOutputStream
							.write("\n".getBytes()))
						fileOutputStream.write(codeString.getBytes());
					fileOutputStream.write(codeString.getBytes());
					fileOutputStream.write("\n".getBytes());
					value = bufferedReader.readLine();
					String insertValue = blockValues.get(value.replaceAll(" ", ""));
					if (insertValue != null) {
						fileOutputStream.write(value.getBytes());
						fileOutputStream.write("\n".getBytes());
						for (; (codeString = bufferedReader.readLine()) != null && !"1".equals(codeString.replaceAll(" ", "")); fileOutputStream
								.write("\n".getBytes()))
							fileOutputStream.write(codeString.getBytes());
						fileOutputStream.write(codeString.getBytes());
						bufferedReader.readLine();
						fileOutputStream.write("\n".getBytes());
						fileOutputStream.write(insertValue.getBytes());
						fileOutputStream.write("\n".getBytes());
					} else {
						fileOutputStream.write(value.getBytes());
						fileOutputStream.write("\n".getBytes());
					}
				}
				if ("100".equals(codeString.replaceAll(" ", "")) && "AcDbLayerTableRecord".equals(value.replaceAll(" ", ""))) {
					for (; (codeString = bufferedReader.readLine()) != null && !"2".equals(codeString.replaceAll(" ", "")); fileOutputStream
							.write("\n".getBytes()))
						fileOutputStream.write(codeString.getBytes());
					fileOutputStream.write(codeString.getBytes());
					fileOutputStream.write("\n".getBytes());
					value = bufferedReader.readLine();
					Boolean insertValue = layerValues.get(value.replaceAll(" ", ""));
					fileOutputStream.write(value.getBytes());
					fileOutputStream.write("\n".getBytes());
					if (insertValue != null && insertValue) {
						for (; (codeString = bufferedReader.readLine()) != null && !"62".equals(codeString.replaceAll(" ", "")); fileOutputStream
								.write("\n".getBytes()))
							fileOutputStream.write(codeString.getBytes());
						fileOutputStream.write(codeString.getBytes());
						// Strat Mithlesh Singh
						// bufferedReader.readLine();
						String negCodeString = bufferedReader.readLine();
						negCodeString = negCodeString.replaceAll(" ", "");
						if (negCodeString.contains("-"))
							negCodeString = negCodeString.replace("-", "");
						fileOutputStream.write("\n".getBytes());
						// fileOutputStream.write("1".getBytes());
						fileOutputStream.write(negCodeString.getBytes());
						// End Mithlesh Singh
						fileOutputStream.write("\n".getBytes());
					} else if (insertValue != null && !insertValue) {
						for (; (codeString = bufferedReader.readLine()) != null && !"62".equals(codeString.replaceAll(" ", "")); fileOutputStream
								.write("\n".getBytes()))
							fileOutputStream.write(codeString.getBytes());
						fileOutputStream.write(codeString.getBytes());
						// Strat Mithlesh Singh
						// bufferedReader.readLine();
						String negCodeString = bufferedReader.readLine();
						negCodeString = negCodeString.replaceAll(" ", "");
						if (!negCodeString.contains("-"))
							negCodeString = "-" + negCodeString;
						fileOutputStream.write("\n".getBytes());
						// fileOutputStream.write("-1".getBytes());
						fileOutputStream.write(negCodeString.getBytes());
						// End Mithlesh Singh
						fileOutputStream.write("\n".getBytes());
					}

				}
			}
			if (codeString != null) {
				fileOutputStream.write(codeString.getBytes());
				fileOutputStream.write("\n".getBytes());
			}
			if (value != null) {
				fileOutputStream.write(value.getBytes());
				fileOutputStream.write("\n".getBytes());
			}
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void parseDXF(File file, File tempDXH, Map<String, String> blockValues, Map<String, Boolean> layerValues,
			List<String> attachedFiles) {

		try {
			String codeString = null;
			String value = null;
			// BufferedReader bufferedReader = new BufferedReader(new
			// FileReader(file));

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempDXH), StandardCharsets.UTF_8));

			while ((codeString = bufferedReader.readLine()) != null && (value = bufferedReader.readLine()) != null) {
				if ("100".equals(codeString.replaceAll(" ", "")) && "AcDbRasterImageDef".equals(value.replaceAll(" ", ""))) {
					codeWrite(bufferedWriter, codeString);
					codeWrite(bufferedWriter, value);
					for (; (codeString = bufferedReader.readLine()) != null && !"1".equals(codeString.replaceAll(" ", "")); bufferedWriter
							.write("\n"))
						codeWriteWithoutNewLine(bufferedWriter, codeString);

					codeWrite(bufferedWriter, codeString);
					value = bufferedReader.readLine();
					if (value.contains(".jpg") || value.contains(".png")) {
						if (!attachedFiles.isEmpty()) {
							for (int i = 0; i < attachedFiles.size(); i++) {

								if (value.contains(attachedFiles.get(i)))
									codeWrite(bufferedWriter, blockValues.get(attachedFiles.get(i)));
							}
						} else {
							codeWrite(bufferedWriter, "\\offer\\noimage.jpg");
						}
						if (value.contains("perfChart.png"))
							codeWrite(bufferedWriter, blockValues.get("perfChart.png"));

					} else
						codeWrite(bufferedWriter, value);
				} else if ("100".equals(codeString.replaceAll(" ", "")) && "AcDbBlockReference".equals(value.replaceAll(" ", ""))) {
					codeWrite(bufferedWriter, codeString);
					codeWrite(bufferedWriter, value);
					for (; (codeString = bufferedReader.readLine()) != null && !"2".equals(codeString.replaceAll(" ", "")); bufferedWriter
							.write("\n"))
						codeWriteWithoutNewLine(bufferedWriter, codeString);
					codeWrite(bufferedWriter, codeString);
					value = bufferedReader.readLine();
					String insertValue = blockValues.get(value.replaceAll(" ", ""));
					if (insertValue != null) {
						codeWrite(bufferedWriter, value);
						for (; (codeString = bufferedReader.readLine()) != null && !"1".equals(codeString.replaceAll(" ", "")); bufferedWriter
								.write("\n"))
							codeWriteWithoutNewLine(bufferedWriter, codeString);
						bufferedWriter.write(codeString);
						bufferedReader.readLine();
						bufferedWriter.write("\n");
						codeWrite(bufferedWriter, insertValue);
					} else {
						codeWrite(bufferedWriter, value);
					}
				} else if ("0".equals(codeString.replaceAll(" ", "")) && "TEXT".equals(value.replaceAll(" ", ""))) {
					codeWrite(bufferedWriter, codeString);
					codeWrite(bufferedWriter, value);
					for (; (codeString = bufferedReader.readLine()) != null && !"1".equals(codeString.replaceAll(" ", "")); bufferedWriter
							.write("\n"))
						codeWriteWithoutNewLine(bufferedWriter, codeString);
					codeWrite(bufferedWriter, codeString);
					value = bufferedReader.readLine();
					String insertValue = blockValues.get(value.replaceAll(" ", ""));
					if (insertValue != null) {
						codeWrite(bufferedWriter, insertValue);
					} else {
						codeWrite(bufferedWriter, value);
					}
				} else {
					codeWrite(bufferedWriter, codeString);
					codeWrite(bufferedWriter, value);
				}
			}

			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void codeWrite(BufferedWriter fileOutputStream, String codeString) throws IOException {

		if ("AcDbBlockReference".equals(codeString.replaceAll(" ", "")))
			System.out.println();
		fileOutputStream.write(codeString);
		fileOutputStream.write("\n");
	}

	private static void codeWriteWithoutNewLine(BufferedWriter fileOutputStream, String codeString) throws IOException {

		if ("AcDbBlockReference".equals(codeString.replaceAll(" ", "")))
			System.out.println();
		fileOutputStream.write(codeString);
	}

}
