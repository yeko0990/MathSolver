package yekocalc;

public class ConditionDecisionTreeNode extends DecisionTreeNode {
	Condition condition;
	DecisionTreeNode truePath, falsePath;
	
	public ConditionDecisionTreeNode(Condition conditionP, DecisionTreeNode trueNode, DecisionTreeNode falseNode) {
		condition = conditionP;
		truePath = trueNode;
		falsePath = falseNode;
	}
	
	@Override
	public void performAction() {
		if (condition.resolve()) truePath.performAction();
		else falsePath.performAction();
	}

}
