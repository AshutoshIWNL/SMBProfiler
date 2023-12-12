package com.informatica.b2btools.smbp.utils;

import com.informatica.b2btools.smbp.exception.SMBPException;
import com.informatica.b2btools.smbp.model.SMBStats;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The ReportGenerator class provides utility methods for generating reports based on SMB profiling statistics.
 * It includes functionality to append profiling information to a file and calculate average times for SMB operations.
 * Also generates a bar chart for average times.
 * @author asmishra
 * @since 11-12-2023
 */
public class ReportGenerator {

    /**
     * Appends SMB profiling statistics to a file and calculates average times for SMB operations.
     * @param smbStats The list of SMBStats containing profiling information for each run.
     * @param statsFile The file path where the profiling report will be generated.
     * @throws SMBPException Thrown if an exception occurs during the report generation process.
     */
    public static void generateReport(List<SMBStats> smbStats, String statsFile) throws SMBPException {
        try (FileWriter writer = new FileWriter(statsFile, true)) {
            int avgExists = 0;
            int avgIsDir = 0;
            int avgLength = 0;
            writer.append("========================= SMB Operations Report =========================\n\n");
            for(int i = 0; i < smbStats.size(); ++i) {
                writer.append("[Run ").append(String.valueOf(i+1)).append("]");
                writer.append("\nExists Call Time Taken: ").append(smbStats.get(i).getExists_call_time_taken()).append("ms");
                writer.append("\nIsDirectory Call Time Taken: ").append(smbStats.get(i).getIs_directory_call_time_taken()).append("ms");
                writer.append("\nLength Call Time Taken: ").append(smbStats.get(i).getLength_call_time_taken()).append("ms");
                writer.append("\n\n");
                avgExists += Integer.parseInt(smbStats.get(i).getExists_call_time_taken());
                avgIsDir += Integer.parseInt(smbStats.get(i).getIs_directory_call_time_taken());
                avgLength += Integer.parseInt(smbStats.get(i).getLength_call_time_taken());
            }
            avgExists /= smbStats.size();
            avgIsDir /= smbStats.size();
            avgLength /= smbStats.size();

            String chartFile = statsFile.substring(0,statsFile.length() - 3) + ".png";
            createBarChart(avgExists, avgIsDir, avgLength, chartFile);

            writer.append("\n<<Average times>>");
            writer.append("\nExists Call Avg Time: ").append(String.valueOf(avgExists)).append("ms");
            writer.append("\nIsDirectory Call Avg Time: ").append(String.valueOf(avgIsDir)).append("ms");
            writer.append("\nLength Call Avg Time: ").append(String.valueOf(avgLength)).append("ms");
            writer.append("\n\n");
            writer.append("========================= End of SMB Operations Report ======================\n\n");
        } catch (IOException e) {
            throw new SMBPException("Exception occurred during generateReport()", e);
        }
    }

    /**
     * Creates a bar chart to visualize the average time taken for SMB operations.
     *
     * @param avgExists Average time for the 'Exists' operation.
     * @param avgIsDir  Average time for the 'IsDirectory' operation.
     * @param avgLength Average time for the 'Length' operation.
     * @param chartFile File path where the chart will be saved.
     * @throws IOException If an error occurs while saving the chart.
     */
    private static void createBarChart(int avgExists, int avgIsDir, int avgLength, String chartFile) throws IOException {
        CategoryDataset dataset = createDataset(avgExists, avgIsDir, avgLength);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Average SMB Operation Times",
                "Operation Type",
                "Average Time (ms)",
                dataset
        );

        // Save the chart to a file
        ChartUtils.saveChartAsPNG(new File(chartFile), barChart, 600, 400);
    }


    /**
     * Creates a dataset for the bar chart based on the average times for SMB operations.
     *
     * @param avgExists Average time for the 'Exists' operation.
     * @param avgIsDir  Average time for the 'IsDirectory' operation.
     * @param avgLength Average time for the 'Length' operation.
     * @return CategoryDataset representing the data for the chart.
     */
    private static CategoryDataset createDataset(int avgExists, int avgIsDir, int avgLength) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(avgExists, "Average Time", "Exists");
        dataset.addValue(avgIsDir, "Average Time", "IsDirectory");
        dataset.addValue(avgLength, "Average Time", "Length");
        return dataset;
    }
}