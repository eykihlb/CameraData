package com.mydao.kkjk.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mydao.kkjk.device.HvDevice;
import com.mydao.kkjk.device.HvDeviceMatch;
import com.mydao.kkjk.device.HvPlayer;
import com.mydao.kkjk.event.MatchInfo;
import com.mydao.kkjk.event.ResultInfo;
import com.mydao.kkjk.utils.Utils;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import org.springframework.stereotype.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

@Component
public class frmMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField text_camera_ip;
	private JTextField text_vfr_ip;
	private JLabel label_camera_ip;
	private JButton btn_camera_con;
	private JLabel label_vfr_ip;
	private JButton btn_vfr_con;
	private JButton btn_start_match;
	private JButton btn_end_match;
	private JButton btn_soft_trigger;
	private JLabel label_1;
	private JLabel lable_camera_status;
	private JLabel lable_2;
	private	JLabel lable_vfr_status;
	private JButton btn_open_video;
	private JButton btn_close_video;
	private JTextArea textArea_camera;
	private JTextArea textArea_vfr;
	private JTextArea textArea_match;
	private JLabel lable_camera_data;
	private JLabel label_vfr_data;
	private JLabel label_match_data;
	private JScrollPane scrollPane_camera;
	private JButton btn_camera_close;
	private JButton btn_vfr_close;
	private JScrollPane scrollPane_vfr;
	private JScrollPane scrollPane_match;
	private JLabel label_6;
	private JButton btn_open_gate;
	private Panel  video_panel;

	//视频是正在播放
	private boolean _is_video_play = false;
	//播放器
	private HvPlayer _player = null;
	//一体机设备
	private HvDevice _camera_device = null;
	//车型设备
	private HvDevice _vfr_device = null;
	//匹配对象
	private HvDeviceMatch _match = null;
	//定时器
	private Timer _timer = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public frmMain() {
		setTitle("HvDeviceDemo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1008, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//播放器对象
		_player = new HvPlayer();
		//一体机设备
		_camera_device = new HvDevice();
		//车型设备
		_vfr_device = new HvDevice();
		//匹配对象
		_match = new HvDeviceMatch();
		//定时器
		_timer = new Timer();

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{72, 156, 57, 57, 117, 129, 102, 120, 110, 0};
		gbl_contentPane.rowHeights = new int[]{23, 23, 15, 281, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		label_camera_ip = new JLabel("\u4E00\u4F53\u673A\u8BBE\u5907IP");
		GridBagConstraints gbc_label_camera_ip = new GridBagConstraints();
		gbc_label_camera_ip.anchor = GridBagConstraints.WEST;
		gbc_label_camera_ip.insets = new Insets(0, 0, 5, 5);
		gbc_label_camera_ip.gridx = 0;
		gbc_label_camera_ip.gridy = 0;
		contentPane.add(label_camera_ip, gbc_label_camera_ip);
		text_camera_ip = new JTextField();
		text_camera_ip.setText("172.18.24.56");
		text_camera_ip.setColumns(10);
		GridBagConstraints gbc_text_camera_ip = new GridBagConstraints();
		gbc_text_camera_ip.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_camera_ip.insets = new Insets(0, 0, 5, 5);
		gbc_text_camera_ip.gridx = 1;
		gbc_text_camera_ip.gridy = 0;
		contentPane.add(text_camera_ip, gbc_text_camera_ip);

		btn_camera_con = new JButton("\u8FDE\u63A5");
		btn_camera_con.setEnabled(true);
		GridBagConstraints gbc_btn_camera_con = new GridBagConstraints();
		gbc_btn_camera_con.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_camera_con.insets = new Insets(0, 0, 5, 5);
		gbc_btn_camera_con.gridx = 2;
		gbc_btn_camera_con.gridy = 0;
		contentPane.add(btn_camera_con, gbc_btn_camera_con);

		btn_camera_close = new JButton("\u5173\u95ED");
		btn_camera_close.setEnabled(false);
		GridBagConstraints gbc_btn_camera_close = new GridBagConstraints();
		gbc_btn_camera_close.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_camera_close.insets = new Insets(0, 0, 5, 5);
		gbc_btn_camera_close.gridx = 3;
		gbc_btn_camera_close.gridy = 0;
		contentPane.add(btn_camera_close, gbc_btn_camera_close);

		btn_open_video = new JButton("\u6253\u5F00\u4E00\u4F53\u673A\u89C6\u9891");
		btn_open_video.setEnabled(true);
		GridBagConstraints gbc_btn_open_video = new GridBagConstraints();
		gbc_btn_open_video.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_open_video.insets = new Insets(0, 0, 5, 5);
		gbc_btn_open_video.gridx = 4;
		gbc_btn_open_video.gridy = 0;
		contentPane.add(btn_open_video, gbc_btn_open_video);

		btn_start_match = new JButton("\u5F00\u59CB\u63A5\u6536\u5339\u914D\u7ED3\u679C");
		btn_start_match.setEnabled(true);
		GridBagConstraints gbc_btn_start_match = new GridBagConstraints();
		gbc_btn_start_match.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_start_match.insets = new Insets(0, 0, 5, 5);
		gbc_btn_start_match.gridx = 5;
		gbc_btn_start_match.gridy = 0;
		contentPane.add(btn_start_match, gbc_btn_start_match);

		btn_soft_trigger = new JButton("\u4E00\u4F53\u673A\u6293\u62CD");
		GridBagConstraints gbc_btn_soft_trigger = new GridBagConstraints();
		gbc_btn_soft_trigger.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_soft_trigger.insets = new Insets(0, 0, 5, 5);
		gbc_btn_soft_trigger.gridx = 6;
		gbc_btn_soft_trigger.gridy = 0;
		contentPane.add(btn_soft_trigger, gbc_btn_soft_trigger);

		label_1 = new JLabel("\u4E00\u4F53\u673A\u8BBE\u5907\u8FDE\u63A5\u72B6\u6001\uFF1A");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 7;
		gbc_label_1.gridy = 0;
		contentPane.add(label_1, gbc_label_1);

		lable_camera_status = new JLabel("\u672A\u8FDE\u63A5");
		GridBagConstraints gbc_lable_camera_status = new GridBagConstraints();
		gbc_lable_camera_status.anchor = GridBagConstraints.WEST;
		gbc_lable_camera_status.insets = new Insets(0, 0, 5, 0);
		gbc_lable_camera_status.gridx = 8;
		gbc_lable_camera_status.gridy = 0;
		contentPane.add(lable_camera_status, gbc_lable_camera_status);

		label_vfr_ip = new JLabel("\u8F66\u578B\u8BBE\u5907IP");
		GridBagConstraints gbc_label_vfr_ip = new GridBagConstraints();
		gbc_label_vfr_ip.anchor = GridBagConstraints.WEST;
		gbc_label_vfr_ip.insets = new Insets(0, 0, 5, 5);
		gbc_label_vfr_ip.gridx = 0;
		gbc_label_vfr_ip.gridy = 1;
		contentPane.add(label_vfr_ip, gbc_label_vfr_ip);

		text_vfr_ip = new JTextField();
		text_vfr_ip.setText("172.18.24.32");
		text_vfr_ip.setColumns(10);
		GridBagConstraints gbc_text_vfr_ip = new GridBagConstraints();
		gbc_text_vfr_ip.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_vfr_ip.insets = new Insets(0, 0, 5, 5);
		gbc_text_vfr_ip.gridx = 1;
		gbc_text_vfr_ip.gridy = 1;
		contentPane.add(text_vfr_ip, gbc_text_vfr_ip);

		btn_vfr_con = new JButton("\u8FDE\u63A5");
		btn_vfr_con.setEnabled(true);
		GridBagConstraints gbc_btn_vfr_con = new GridBagConstraints();
		gbc_btn_vfr_con.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_vfr_con.insets = new Insets(0, 0, 5, 5);
		gbc_btn_vfr_con.gridx = 2;
		gbc_btn_vfr_con.gridy = 1;
		contentPane.add(btn_vfr_con, gbc_btn_vfr_con);

		btn_vfr_close = new JButton("\u5173\u95ED");
		btn_vfr_close.setEnabled(false);
		GridBagConstraints gbc_btn_vfr_close = new GridBagConstraints();
		gbc_btn_vfr_close.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_vfr_close.insets = new Insets(0, 0, 5, 5);
		gbc_btn_vfr_close.gridx = 3;
		gbc_btn_vfr_close.gridy = 1;
		contentPane.add(btn_vfr_close, gbc_btn_vfr_close);
		btn_close_video = new JButton("\u5173\u95ED\u4E00\u4F53\u673A\u89C6\u9891");
		btn_close_video.setEnabled(false);
		GridBagConstraints gbc_btn_close_video = new GridBagConstraints();
		gbc_btn_close_video.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_close_video.insets = new Insets(0, 0, 5, 5);
		gbc_btn_close_video.gridx = 4;
		gbc_btn_close_video.gridy = 1;
		contentPane.add(btn_close_video, gbc_btn_close_video);

		btn_end_match = new JButton("\u505C\u6B62\u63A5\u6536\u5339\u914D\u7ED3\u679C");
		btn_end_match.setEnabled(false);
		GridBagConstraints gbc_btn_end_match = new GridBagConstraints();
		gbc_btn_end_match.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_end_match.insets = new Insets(0, 0, 5, 5);
		gbc_btn_end_match.gridx = 5;
		gbc_btn_end_match.gridy = 1;
		contentPane.add(btn_end_match, gbc_btn_end_match);

		btn_open_gate = new JButton("\u4E00\u4F53\u673A\u5F00\u95F8");
		GridBagConstraints gbc_btn_open_gate = new GridBagConstraints();
		gbc_btn_open_gate.insets = new Insets(0, 0, 5, 5);
		gbc_btn_open_gate.gridx = 6;
		gbc_btn_open_gate.gridy = 1;
		contentPane.add(btn_open_gate, gbc_btn_open_gate);

		lable_2 = new JLabel("\u8F66\u578B\u8BBE\u5907\u8FDE\u63A5\u72B6\u6001\uFF1A");
		GridBagConstraints gbc_lable_2 = new GridBagConstraints();
		gbc_lable_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_lable_2.insets = new Insets(0, 0, 5, 5);
		gbc_lable_2.gridx = 7;
		gbc_lable_2.gridy = 1;
		contentPane.add(lable_2, gbc_lable_2);

		lable_vfr_status = new JLabel("\u672A\u8FDE\u63A5");
		GridBagConstraints gbc_lable_vfr_status = new GridBagConstraints();
		gbc_lable_vfr_status.anchor = GridBagConstraints.NORTHWEST;
		gbc_lable_vfr_status.insets = new Insets(0, 0, 5, 0);
		gbc_lable_vfr_status.gridx = 8;
		gbc_lable_vfr_status.gridy = 1;
		contentPane.add(lable_vfr_status, gbc_lable_vfr_status);

		label_6 = new JLabel("\u4E00\u4F53\u673A\u8BBE\u5907\u89C6\u9891");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridwidth = 2;
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 2;
		contentPane.add(label_6, gbc_label_6);

		lable_camera_data = new JLabel("\u4E00\u4F53\u673A\u8BBE\u5907\u6570\u636E");
		GridBagConstraints gbc_lable_camera_data = new GridBagConstraints();
		gbc_lable_camera_data.anchor = GridBagConstraints.NORTHWEST;
		gbc_lable_camera_data.insets = new Insets(0, 0, 5, 5);
		gbc_lable_camera_data.gridwidth = 2;
		gbc_lable_camera_data.gridx = 2;
		gbc_lable_camera_data.gridy = 2;
		contentPane.add(lable_camera_data, gbc_lable_camera_data);

		label_vfr_data = new JLabel("\u8F66\u578B\u8BBE\u5907\u6570\u636E");
		GridBagConstraints gbc_label_vfr_data = new GridBagConstraints();
		gbc_label_vfr_data.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_vfr_data.insets = new Insets(0, 0, 5, 5);
		gbc_label_vfr_data.gridx = 5;
		gbc_label_vfr_data.gridy = 2;
		contentPane.add(label_vfr_data, gbc_label_vfr_data);

		label_match_data = new JLabel("\u5339\u914D\u6570\u636E");
		GridBagConstraints gbc_label_match_data = new GridBagConstraints();
		gbc_label_match_data.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_match_data.insets = new Insets(0, 0, 5, 5);
		gbc_label_match_data.gridx = 7;
		gbc_label_match_data.gridy = 2;
		contentPane.add(label_match_data, gbc_label_match_data);

		//视频窗口
		video_panel = new Panel();
		video_panel.setBackground(Color.BLACK);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		contentPane.add(video_panel, gbc_panel);

		scrollPane_camera = new JScrollPane();
		textArea_camera = new JTextArea(8, 20);
		scrollPane_camera.setViewportView(textArea_camera);
		GridBagConstraints gbc_scrollPane_camera = new GridBagConstraints();
		gbc_scrollPane_camera.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_camera.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_camera.gridwidth = 3;
		gbc_scrollPane_camera.gridx = 2;
		gbc_scrollPane_camera.gridy = 3;
		contentPane.add(scrollPane_camera, gbc_scrollPane_camera);

		scrollPane_vfr = new JScrollPane();
		textArea_vfr = new JTextArea(8, 20);
		scrollPane_vfr.setViewportView(textArea_vfr);
		GridBagConstraints gbc_scrollPane_vfr = new GridBagConstraints();
		gbc_scrollPane_vfr.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_vfr.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_vfr.gridwidth = 2;
		gbc_scrollPane_vfr.gridx = 5;
		gbc_scrollPane_vfr.gridy = 3;
		contentPane.add(scrollPane_vfr, gbc_scrollPane_vfr);

		scrollPane_match = new JScrollPane();
		textArea_match = new JTextArea(8, 20);
		scrollPane_match.setViewportView(textArea_match);
		GridBagConstraints gbc_scrollPane_match = new GridBagConstraints();
		gbc_scrollPane_match.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_match.gridwidth = 2;
		gbc_scrollPane_match.gridx = 7;
		gbc_scrollPane_match.gridy = 3;
		contentPane.add(scrollPane_match, gbc_scrollPane_match);

		//获取设备连接状态
		_timer.schedule(new TimerTask(){
			public void run(){
				if(_camera_device != null && _camera_device.GetConnectStatus()){
					lable_camera_status.setText("已连接");
				}
				else{
					lable_camera_status.setText("未连接");
				}

				if(_vfr_device != null && _vfr_device.GetConnectStatus()){
					lable_vfr_status.setText("已连接");
				}
				else{
					lable_vfr_status.setText("未连接");
				}
			}
		}, 1000,2000);

		//连接一体机设备
		btn_camera_con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String camera_ip = text_camera_ip.getText();
				if(camera_ip.equals("") || !Utils.IsIP(camera_ip)){
					JOptionPane.showMessageDialog(null, "一体机设备ip错误,请填写正确的ip!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(_camera_device != null && !_camera_device.OpenDevice(camera_ip, 0)){
					JOptionPane.showMessageDialog(null, "打开一体机设备失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!_camera_device.StartResult(null,null)){
					JOptionPane.showMessageDialog(null, "设置一体机设备结果回调失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				btn_camera_con.setEnabled(false);
				btn_camera_close.setEnabled(true);
			}
		});

		//关闭一体机设备
		btn_camera_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_camera_device != null){
					_camera_device.CloseDevice();
				}

				btn_camera_con.setEnabled(true);
				btn_camera_close.setEnabled(false);
			}
		});

		//连接车型设备
		btn_vfr_con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String vfr_ip = text_vfr_ip.getText();
				if(vfr_ip.equals("") || !Utils.IsIP(vfr_ip)){
					JOptionPane.showMessageDialog(null, "车型设备ip错误,请填写正确的ip!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(_vfr_device != null && !_vfr_device.OpenDevice(vfr_ip, 1)){
					JOptionPane.showMessageDialog(null, "打开车型设备失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!_vfr_device.StartResult(null,null)){
					JOptionPane.showMessageDialog(null, "设置车型设备结果回调失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				btn_vfr_con.setEnabled(false);
				btn_vfr_close.setEnabled(true);
			}
		});

		//关闭车型设备
		btn_vfr_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_vfr_device != null){
					_vfr_device.CloseDevice();
				}

				btn_vfr_con.setEnabled(true);
				btn_vfr_close.setEnabled(false);
			}
		});

		//打开一体机视频
		btn_open_video.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip = text_camera_ip.getText();
				if(ip.equals("") || !Utils.IsIP(ip)){
					JOptionPane.showMessageDialog(null, "一体机ip错误,请填写正确的ip!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				HWND hwnd = new HWND();
				hwnd.setPointer(Native.getComponentPointer(video_panel));
				if(_player != null && !_player.Init(hwnd, ip)){
					JOptionPane.showMessageDialog(null, "播放器初始化失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(!_player.Play()){
					JOptionPane.showMessageDialog(null, "播放器播放视频失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				_is_video_play = true;
				btn_open_video.setEnabled(false);
				btn_close_video.setEnabled(true);
			}
		});

		//关闭视频
		btn_close_video.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!_is_video_play){
					JOptionPane.showMessageDialog(null, "视频未播放不需要关闭!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(_player != null){
					_player.Stop();
				}

				_is_video_play = false;
				btn_open_video.setEnabled(true);
				btn_close_video.setEnabled(false);
			}
		});

		//一体机抓拍
		btn_soft_trigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_camera_device != null){
					if(!_camera_device.SoftTriggerCapture()){
						JOptionPane.showMessageDialog(null, "一体机设备抓拍失败!", "提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		//开始接收匹配结果
		btn_start_match.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_camera_device == null || _vfr_device == null){
					JOptionPane.showMessageDialog(null, "一体机设备或车型设备对象未创建!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Pointer camera_handle = _camera_device.GetDeviceHandle();
				Pointer vfr_handle = _vfr_device.GetDeviceHandle();
				if(camera_handle == null || vfr_handle == null){
					JOptionPane.showMessageDialog(null, "一体机设备或车型设备句柄为空!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(!_match.StartMatchResult(camera_handle, vfr_handle, 120, 120, 120, 120)){
					JOptionPane.showMessageDialog(null, "设置一体机设备和车型设备匹配回调失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				btn_start_match.setEnabled(false);
				btn_end_match.setEnabled(true);
			}
		});

		//停止接收匹配结果(注意：先关匹配结果再关闭设备连接)
		btn_end_match.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_match == null){
					JOptionPane.showMessageDialog(null, "匹配对象未创建!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(!HvDeviceMatch.StopAllMatchResult()){
					JOptionPane.showMessageDialog(null, "关闭匹配回调失败!", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}

				btn_start_match.setEnabled(true);
				btn_end_match.setEnabled(false);
			}
		});

		//一体机开闸
		btn_open_gate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_camera_device != null){
					if(_camera_device.TriggerBarrierSignal()){
						JOptionPane.showMessageDialog(null, "开闸成功!", "提示", JOptionPane.DEFAULT_OPTION);
					}
					else{
						JOptionPane.showMessageDialog(null, "开闸失败!", "提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

	}

	//显示一体机数据
	public void ShowResultInfo(ResultInfo result){
		StringBuffer sb = new StringBuffer();

		//一体机数据
		if(result.type == 0){
			sb.append(String.format("接收时间:%s\n",result.time));
			sb.append(String.format("车辆ID号:%d\n",result.carID));
			sb.append(String.format("车牌号:%s\n",result.plateNo));
			sb.append(String.format("车牌颜色:%s\n",result.plateNoColor));
			sb.append(String.format("小图路径:%s\n",result.smallPicPath));
			sb.append(String.format("大图路径:%s\n",result.lastBigPicPath));
			sb.append(String.format("附加信息:[%s]\n",result.appendInfo.trim()));

			String new_text = sb.toString();
			String before_text = textArea_camera.getText();

			String text = "";
			if(before_text.equals("")){
				text = new_text;
			}
			else{
				text = before_text + "\n" +  new_text;
			}

			textArea_camera.setText(text);
			textArea_camera.setCaretPosition(textArea_camera.getText().length());
		}
		//车型设备数据
		else{
			sb.append(String.format("接收时间:%s\n",result.time));
			sb.append(String.format("车辆ID号:%d\n",result.carID));
			sb.append(String.format("车牌号:%s\n",result.plateNo));
			sb.append(String.format("车牌颜色:%s\n",result.plateNoColor));
			sb.append(String.format("小图路径:%s\n",result.smallPicPath));
			sb.append(String.format("车头图路径:%s\n",result.headPicPath));
			sb.append(String.format("车身图路径:%s\n",result.bodyPicPath));
			sb.append(String.format("车尾图路径:%s\n",result.tailPicPath));
			sb.append(String.format("附加信息:[%s]\n",result.appendInfo.trim()));

			String new_text = sb.toString();
			String before_text = textArea_vfr.getText();

			String text = "";
			if(before_text.equals("")){
				text = new_text;
			}
			else{
				text = before_text + "\n" +  new_text;
			}

			textArea_vfr.setText(text);
			textArea_vfr.setCaretPosition(textArea_vfr.getText().length());
		}
	}

	//显示匹配数据
	public void ShowMatchInfo(MatchInfo match){
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("一体机设备GUID:%s\n",match.cameraGuid));
		sb.append(String.format("车型设备GUID:%s\n",match.vfrGuid));

		String new_text = sb.toString();
		String before_text = textArea_match.getText();

		String text = "";
		if(before_text.equals("")){
			text = new_text;
		}
		else{
			text = before_text + "\n" +  new_text;
		}

		textArea_match.setText(text);
		textArea_match.setCaretPosition(textArea_match.getText().length());
	}

}
