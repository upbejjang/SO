package com.pineone.icbms.so.iot.creator;

import java.util.ArrayList;
import java.util.List;

import com.pineone.icbms.so.resources.context.IGenericContext;
import com.pineone.icbms.so.resources.model.repo.task.DefaultTaskModel;
import com.pineone.icbms.so.resources.processor.ITaskCreator;
import com.pineone.icbms.so.resources.service.DefaultService;
import com.pineone.icbms.so.resources.task.DefaultTask;
import com.pineone.icbms.so.resources.task.IGenericTask;

/**
 * Created by existmaster on 2016. 1. 8..
 */
public class DefaultTaskCreator
		implements ITaskCreator<IGenericTask, DefaultTaskModel, IGenericContext>
{
    private final DefaultActivityCreator defaultActivityCreator;

    /**
     * Construct
     */
    public DefaultTaskCreator() {
        defaultActivityCreator = new DefaultActivityCreator();
    }

    /**
	 * Create Tasks.
	 * 
	 * @param taskModelList
	 *            taskModelList
	 * @return defaultTaskList
	 */
	@Override
	public List<IGenericTask> createTasks(List<DefaultTaskModel> taskModelList, IGenericContext context)
	{
		ArrayList<IGenericTask> taskList = new ArrayList<IGenericTask>();
		for (DefaultTaskModel taskModel : taskModelList)
		{
			taskList.add(createTask(taskModel, context));
		}
		return taskList;
	}

	/**
	 * Create Task.
	 *
	 * @param taskModel
	 *            TaskModel
	 * @return defaultTask
	 */
	@Override
	public IGenericTask createTask(DefaultTaskModel taskModel, IGenericContext context )
	{
		DefaultTask task = new DefaultTask();
		task.setActivityList(defaultActivityCreator.createActivities(taskModel.getActivityModelList(), context));
		task.setName(taskModel.getName());
		task.setId(taskModel.getId()+"_"+System.nanoTime());

		return task;
	}
}
