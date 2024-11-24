package weightcalcswing;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class WeightCalcSwingApp {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// 画面描画
				Frame frame = new Frame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

@SuppressWarnings("serial")
class Frame extends JFrame {
//	   CardLayout layout;
	JPanel cardPanel = new JPanel();
	CardLayout layout = new CardLayout();

	public Frame() {
		// ウィンドウ設定
		setTitle("健康管理アプリ by Swing");
		setSize(750, 300);

		// フォント一括設定
		FontUtility fontUtility = new FontUtility();
		fontUtility.setFont(new Font(Font.DIALOG, Font.PLAIN, 28));

		// サイズのテンプレ
		// テキストフィールド
		Dimension inputFieldDimension = new Dimension(250, 40);
		// ボタン
		Dimension buttonDimension = new Dimension(200, 40);

		/* トップ画面 ***********************************************************/
		// トップのメッセージ領域
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(new JLabel("健康管理アプリケーションへようこそ", SwingConstants.CENTER));

		// 名前入力の領域
		JPanel namePanel = new JPanel();
		final JTextField nameField = new JTextField();
		nameField.setPreferredSize(inputFieldDimension);
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		namePanel.add(new JLabel("名前:", SwingConstants.LEFT));
		namePanel.add(nameField);

		// 身長入力の領域
		JPanel heightPanel = new JPanel();
		final JTextField heightField = new JTextField();
		heightField.setPreferredSize(inputFieldDimension);
		heightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		heightPanel.add(new JLabel("身長:", SwingConstants.LEFT));
		heightPanel.add(heightField);
		heightPanel.add(new JLabel("m", SwingConstants.LEFT));

		// 体重入力の領域
		JPanel weighPanel = new JPanel();
		final JTextField weightField = new JTextField();
		weightField.setPreferredSize(inputFieldDimension);
		weighPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		weighPanel.add(new JLabel("体重:", SwingConstants.LEFT));
		weighPanel.add(weightField);
		weighPanel.add(new JLabel("kg", SwingConstants.LEFT));

		// 情報入力の領域
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.add(namePanel);
		inputPanel.add(heightPanel);
		inputPanel.add(weighPanel);

		// ボタンの領域
		JPanel topStartButtonPanel = new JPanel();
		final JButton topStartButton = new JButton("開始");
		topStartButton.setPreferredSize(buttonDimension);
		topStartButtonPanel.add(topStartButton);

		JPanel topCloseButtonPanel = new JPanel();
		JButton topCloseButton = new JButton("閉じる");
		topCloseButton.setPreferredSize(buttonDimension);
		topCloseButtonPanel.add(topCloseButton);

		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setLayout(new BoxLayout(topButtonPanel, BoxLayout.Y_AXIS));
		topButtonPanel.add(topStartButtonPanel);
		topButtonPanel.add(topCloseButtonPanel);

		// メイン画面の領域
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		mainPanel.add(inputPanel);
		mainPanel.add(topButtonPanel);

		// トップ画面に当たるパネル
		JPanel topPagePanel = new JPanel();
		topPagePanel.setLayout(new BoxLayout(topPagePanel, BoxLayout.Y_AXIS));
		topPagePanel.add(topPanel);
		topPagePanel.add(mainPanel);

		/* 計算結果画面 ***********************************************************/
		// BMI結果の領域
		JPanel bmiValuePanel = new JPanel();
		bmiValuePanel.setLayout(new BoxLayout(bmiValuePanel, BoxLayout.X_AXIS));
		JLabel bmiValueLabel = new JLabel("");
		bmiValuePanel.add(new JLabel("BMI:"));
		bmiValuePanel.add(bmiValueLabel);

		// BMIコメントの領域
		JPanel bmiCommentPanel = new JPanel();
		JLabel bmiCommentLabel = new JLabel("");
		bmiCommentPanel.setLayout(new BoxLayout(bmiCommentPanel, BoxLayout.X_AXIS));
		bmiCommentPanel.add(bmiCommentLabel);

		// 標準体重コメントの領域
		JPanel standardWeightCommentPanel = new JPanel();
		JLabel standardWeightCommentLabel = new JLabel("");
		standardWeightCommentPanel.setLayout(new BoxLayout(standardWeightCommentPanel, BoxLayout.X_AXIS));
		standardWeightCommentPanel.add(standardWeightCommentLabel);

		// ボタンの領域
		JPanel calcSendTopButtonPanel = new JPanel();
		final JButton calcSendTopButton = new JButton("入力画面へ");
		calcSendTopButton.setPreferredSize(buttonDimension);
		calcSendTopButtonPanel.add(calcSendTopButton);

		JPanel calcCloseButtonPanel = new JPanel();
		JButton calcCloseButton = new JButton("閉じる");
		calcCloseButton.setPreferredSize(buttonDimension);
		calcCloseButtonPanel.add(calcCloseButton);

		JPanel calcButtonPanel = new JPanel();
		calcButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		calcButtonPanel.add(calcSendTopButtonPanel);
		calcButtonPanel.add(calcCloseButtonPanel);

		// 計算結果の画面に当たるパネル
		JPanel calcPagePanel = new JPanel();
		calcPagePanel.setLayout(new BoxLayout(calcPagePanel, BoxLayout.Y_AXIS));

		calcPagePanel.add(bmiValuePanel);
		calcPagePanel.add(bmiCommentPanel);
		calcPagePanel.add(standardWeightCommentPanel);
		calcPagePanel.add(calcButtonPanel);

		/************************************************************/

		// CardLayoutにトップ画面、計算結果画面を入れる
		cardPanel.setLayout(layout);

		cardPanel.add(topPagePanel, "topPagePanel");
		cardPanel.add(calcPagePanel, "calcPagePanel");

		// CardLayoutを配置
		Container container = getContentPane();
		container.add(cardPanel);

		/* ボタンイベント定義 ***********************************************************/
		// NOTE: イベントは外だししたほうがすっきりすると思うが、単純に外だしするだけだと参照してるオブジェクト等にアクセスできなくなるため、いったん妥協する
		// 開始ボタンの押下イベント
		ActionListener startAction = e -> {

			// 入力チェック
			InputValidator validator = new InputValidator();
			boolean validated = true;
			List<String> message = new ArrayList<String>();
			message.add("正しい入力がされませんでした。\n以下条件を満たす文字を再度入力してください。");

			// 名前の入力チェック
			if (!validator.validName(nameField.getText())) {
				message.add("名前：アルファベットの大文字、小文字、半角スペース、ピリオドのみ");
				validated = false;
			}

			// 身長の入力チェック
			if (!validator.validHeight(heightField.getText())) {
				message.add("身長：'0.0'～'3.0'の範囲内の数字");
				validated = false;
			}

			// 体重の入力チェック
			if (!validator.validWeight(weightField.getText())) {
				message.add("体重：'0'～'500'の範囲内の数字");
				validated = false;
			}

			if (!validated) {
				// 入力エラー時、メッセージ表示し、トップ画面にとどまる
				JOptionPane.showMessageDialog(null, String.join("\n", message));
				return;
			}

			// ユーザ情報セット
			User user = new User();
			user.setName(nameField.getText());
			user.setHeight(Double.valueOf(heightField.getText()));
			user.setWeight(Double.valueOf(weightField.getText()));

			String viewBmiValue = "-";
			String viewBmiComment = "";
			String viewStandardWeightComment = "";

			// TODO: ここら辺の計算とかはUserクラス内でやってもいいかも？
			// BMI計算
			if (user.height == 0) {
				// 0除算回避
				viewBmiComment = "身長が0の場合はBMIの計算ができません。";
			} else {
				double bmi = user.weight / user.height / user.height;
				// 小数点以下2位で切り捨て
				bmi = Math.floor(bmi * 100) / 100; 

				viewBmiValue = String.format("%.2f", bmi);
				if (bmi < 18.5) {
					viewBmiComment = String.format("%sさんはやせています。", user.name);
				} else if (bmi >= 18.5 && bmi < 25) {
					viewBmiComment = String.format("%sさんはふつうです。", user.name);
				} else {
					viewBmiComment = String.format("%sさんはふとっています。", user.name);
				}
			}

			// 標準体重計算
			double standardWeight = user.height * user.height * 22;
			viewStandardWeightComment = String.format("%sさんの標準体重は%.2fkgです。", user.name, standardWeight);

			// 各種表示内容をセット
			bmiValueLabel.setText(viewBmiValue);
			bmiCommentLabel.setText(viewBmiComment);
			standardWeightCommentLabel.setText(viewStandardWeightComment);

			// 計算結果画面へ遷移
			layout.show(cardPanel, "calcPagePanel");

		};

		// 閉じるボタンの押下イベント
		ActionListener closeAction = e -> {
			JOptionPane.showMessageDialog(null, "ありがとうございました。");
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(topCloseButton);
			if (frame != null) {
				frame.dispose();
			}
		};

		// 入力画面へ戻るボタンの押下イベント
		ActionListener sendTopAction = e -> {
			layout.show(cardPanel, "topPagePanel");
		};

		/* イベントセット ***********************************************************/
		// トップ画面の"開始"ボタン
		topStartButton.addActionListener(startAction);
		// トップボタンの"閉じる"ボタン
		topCloseButton.addActionListener(closeAction);
		// 計算結果画面の"入力画面へ戻る"ボタン
		calcSendTopButton.addActionListener(sendTopAction);
		// 計算結果画面の"閉じる"ボタン
		calcCloseButton.addActionListener(closeAction);
	}

}

// NOTE: これをやると、何の表示もなくテキストエリアの移動ができなくなり、操作感が悪いため、別でバリデートする
//class DoubleInputVerifier extends InputVerifier {
//	@Override
//	public boolean verify(JComponent c) {
//		boolean verified = false;
//		JTextField textField = (JTextField) c;
//		try {
//			Double.valueOf(textField.getText());
//			verified = true;
//		} catch (NumberFormatException e) {
//			UIManager.getLookAndFeel().provideErrorFeedback(c);
//		}
//		return verified;
//	}
//}

/**
 * 入力チェッククラス
 */
class InputValidator {
	/**
	 * 名前の入力チェック処理
	 * 
	 * @param name: 名前
	 * @return: true/false
	 */
	public boolean validName(String name) {
		boolean validated = false;
		if (name != null && !name.isEmpty() && name.matches("^[a-zA-Z .]*$")) {
			// ローマ字の場合のみ名前と認める(半角スペースと.は許容する)
			validated = true;
		}
		return validated;
	}

	/**
	 * 身長の入力チェック処理
	 * 
	 * @param strHeight: 身長（文字列）
	 * @return: true/false
	 */
	public boolean validHeight(String strHeight) {
		boolean validated = false;
		try {
			Double height = Double.valueOf(strHeight);
			if (height >= 0.0 && height <= 3.0) {
				// 0.0m～3.0mの場合のみ身長と認める
				validated = true;
			}
		} catch (NumberFormatException e) {
			// 握りつぶす（基本的にdoubleに変換できなかった場合に来る）
		}
		return validated;
	}

	/**
	 * 体重の入力チェック処理
	 * 
	 * @param strWeight: 体重（文字列）
	 * @return: true/false
	 */
	public boolean validWeight(String strWeight) {
		boolean validated = false;
		try {
			Double inputDouble = Double.parseDouble(strWeight);
			if (inputDouble >= 0.0 && inputDouble <= 500.0) {
				// 0kg～500kgの場合のみ体重と認める
				validated = true;
			}
		} catch (Exception e) {
			// 握りつぶす（基本的にdoubleに変換できなかった場合に来る）
		}
		return validated;
	}
}

/**
 * フォント操作を行うクラス
 * <p>
 *
 */
class FontUtility {

	/**
	 * フォントの一括反映
	 * 
	 * @param font 統一するフォント
	 */
	public static void setFont(Font font) {
		UIManager.put("Button.font", font);
		UIManager.put("ToggleButton.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("CheckBox.font", font);
		UIManager.put("ColorChooser.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("Label.font", font);
		UIManager.put("List.font", font);
		UIManager.put("MenuBar.font", font);
		UIManager.put("MenuItem.font", font);
		UIManager.put("MenuItem.acceleratorFont", font);
		UIManager.put("RadioButtonMenuItem.font", font);
		UIManager.put("RadioButtonMenuItem.acceleratorFont", font);
		UIManager.put("CheckBoxMenuItem.font", font);
		UIManager.put("CheckBoxMenuItem.acceleratorFont", font);
		UIManager.put("Menu.font", font);
		UIManager.put("Menu.acceleratorFont", font);
		UIManager.put("PopupMenu.font", font);
		UIManager.put("OptionPane.font", font);
		UIManager.put("Panel.font", font);
		UIManager.put("ProgressBar.font", font);
		UIManager.put("ScrollPane.font", font);
		UIManager.put("ViewPort.font", font);
		UIManager.put("TabbedPane.font", font);
		UIManager.put("Table.font", font);
		UIManager.put("TableHeader.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("PasswordField.font", font);
		UIManager.put("TextArea.font", font);
		UIManager.put("TextPane.font", font);
		UIManager.put("EditorPane.font", font);
		UIManager.put("TitledBorder.font", font);
		UIManager.put("ToolBar.font", font);
		UIManager.put("ToolTip.font", font);
		UIManager.put("Tree.font", font);
	}
}